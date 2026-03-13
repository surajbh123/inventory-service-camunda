package com.example.loanapplication.postgres;

import com.example.loanapplication.inventory.Inventory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class InventoryJdbcRepository {

    private final NamedParameterJdbcTemplate jdbc;
    private final SimpleJdbcInsert insert;

    public InventoryJdbcRepository(NamedParameterJdbcTemplate jdbc, DataSource ds) {
        this.jdbc = jdbc;
        this.insert = new SimpleJdbcInsert(ds).withTableName("inventory").usingGeneratedKeyColumns("id");
    }

    private final RowMapper<Inventory> rowMapper = (rs, rowNum) -> mapRow(rs);

    private Inventory mapRow(ResultSet rs) throws SQLException {
        Inventory inv = new Inventory();
        long id = rs.getLong("id");
        inv.setId(rs.wasNull() ? null : id);
        inv.setName(rs.getString("name"));
        inv.setQuantity(rs.getInt("quantity"));
        long parent = rs.getLong("parent_id");
        inv.setParentId(rs.wasNull() ? null : parent);
        java.sql.Timestamp ts = rs.getTimestamp("updated_at");
        if (ts != null) {
            inv.setUpdatedAt(ts.toInstant().atOffset(ZoneOffset.UTC));
        }
        return inv;
    }

    public Optional<Inventory> findById(Long id) {
        String sql = "SELECT * FROM inventory WHERE id = :id";
        Map<String, Object> params = Collections.singletonMap("id", id);
        try {
            return Optional.ofNullable(jdbc.queryForObject(sql, params, rowMapper));
        } catch (org.springframework.dao.EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }

    public List<Inventory> findAll() {
        String sql = "SELECT * FROM inventory ORDER BY id";
        return jdbc.query(sql, new MapSqlParameterSource(), rowMapper);
    }

    /** Example complex query using a self-join to fetch parent name alongside item */
    public List<Map<String, Object>> findAllWithParentName() {
        String sql = "SELECT i.id, i.name, i.quantity, i.parent_id, p.name AS parent_name " +
                     "FROM inventory i LEFT JOIN inventory p ON i.parent_id = p.id ORDER BY i.id";
        return jdbc.getJdbcOperations().queryForList(sql);
    }

    public long insert(Inventory inv) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", inv.getName());
        params.put("quantity", inv.getQuantity() == null ? 0 : inv.getQuantity());
        params.put("parent_id", inv.getParentId());
        Number key = insert.executeAndReturnKey(new MapSqlParameterSource(params));
        return key.longValue();
    }

    public int updateQuantity(Long id, int delta) {
        String sql = "UPDATE inventory SET quantity = quantity + :delta, updated_at = NOW() WHERE id = :id";
        Map<String, Object> params = Map.of("delta", delta, "id", id);
        return jdbc.update(sql, params);
    }

    public int deleteById(Long id) {
        String sql = "DELETE FROM inventory WHERE id = :id";
        return jdbc.update(sql, Collections.singletonMap("id", id));
    }
}
