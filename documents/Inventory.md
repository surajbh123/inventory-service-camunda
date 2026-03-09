# Inventory Management Scenarios

---

## 1. Inventory Refill Decision

**Scenario:**
- System checks inventory levels periodically.
- If stock goes below a threshold, it triggers a refill process.

**BPMN Workflow:**
- Start Event: Scheduled inventory check
- Service Task: Fetch current stock
- Business Rule Task: Call DMN Inventory Reorder Decision
- Gateway:
  - If reorder required: Create purchase order
  - Else: End process
- End Event

**DMN Decision Table: Reorder Decision**

| Current Stock | Reorder Level | Action    |
|--------------|--------------|-----------|
| < 100        | 150          | Reorder   |
| 100-200      | 150          | Monitor   |
| > 200        | 150          | No action |

**Real World Example:**
- Warehouse checks stock daily.
- If product quantity < safety stock, system automatically triggers supplier order.

---

## 2. Seasonal Demand Impact

**Scenario:**
- Certain seasons increase demand (festivals, holidays).

**Examples:**
- Winter: jackets demand
- Summer: cold drinks demand
- Diwali: electronics demand

**BPMN Workflow:**
- Start Event: Monthly demand planning
- Service Task: Fetch historical sales
- Business Rule Task: Season Impact Decision
- Gateway: Adjust reorder quantity

**DMN Decision Table: Season Impact**

| Season   | Demand Impact | Stock Increase |
|----------|--------------|---------------|
| Summer   | High         | +40%          |
| Winter   | Medium       | +20%          |
| Festival | Very High    | +60%          |
| Normal   | Low          | 0%            |

**Real World Example:**
- Before Diwali, e-commerce companies increase electronics inventory.

---

## 3. High Demand Detection

**Scenario:**
- Inventory system monitors sales rate.
- If demand suddenly increases, stock must be replenished faster.

**BPMN Workflow:**
- Start Event: Sales event
- Service Task: Calculate sales velocity
- Business Rule Task: Demand Level Decision
- Gateway:
  - Normal demand: Continue
  - High demand: Trigger fast procurement
  - Extreme demand: Emergency restock

**DMN Decision Table: Demand Level**

| Sales Rate / Day | Demand Level | Action           |
|------------------|-------------|------------------|
| < 50             | Normal      | No change        |
| 50–100           | Medium      | Increase reorder |
| 100–200          | High        | Fast restock     |
| > 200            | Critical    | Emergency supply |

**Real World Example:**
- If a product goes viral, the system immediately increases procurement frequency.

---

## 4. Pre-Season Inventory Planning

**Scenario:**
- Before high demand season arrives, inventory must be increased in advance.

**BPMN Workflow:**
- Start Event: Season approaching
- Service Task: Get season calendar
- Business Rule Task: Season Preparation Decision
- Gateway: Increase stock before demand spike

**DMN Decision Table: Season Preparation**

| Days Before Season | Demand Forecast | Action         |
|-------------------|----------------|---------------|
| >60               | Medium         | Plan purchase  |
| 30–60             | High           | Increase stock |
| <30               | Very High      | Urgent restock |

**Example:**
- Before Black Friday, retailers stock warehouses weeks in advance.

---

## 5. Supplier Lead Time Decision

**Scenario:**
- Different suppliers take different time to deliver.

**BPMN Workflow:**
- Start Event: Reorder triggered
- Service Task: Check supplier lead time
- Business Rule Task: Supplier Selection Decision
- Gateway: Place order with best supplier

**DMN Decision Table: Supplier Selection**

| Supplier    | Lead Time | Cost   | Decision        |
|-------------|-----------|--------|-----------------|
| Supplier A  | 2 days    | High   | Use for urgent  |
| Supplier B  | 7 days    | Low    | Use normal      |
| Supplier C  | 4 days    | Medium | Balanced        |

---

## 6. Overstock Prevention

**Scenario:**
- Too much stock increases warehouse cost.

**BPMN Workflow:**
- Start Event: Inventory analysis
- Service Task: Check storage capacity
- Business Rule Task: Overstock Decision
- Gateway:
  - Stop reorder or apply discount sale

**DMN Decision Table: Overstock Prevention**

| Stock Level | Warehouse Capacity | Action        |
|-------------|-------------------|--------------|
| <70%        | Available         | Allow reorder |
| 70–90%      | Limited           | Reduce reorder|
| >90%        | Full              | Stop reorder  |

---

## 7. Expiry-Based Inventory Management

**Scenario:**
- For perishable products.

**BPMN Workflow:**
- Start Event: Daily check
- Service Task: Check expiry dates
- Business Rule Task: Expiry Handling Decision
- Gateway: Discount / Remove / Return

**DMN Decision Table: Expiry Handling**

| Days to Expiry | Action        |
|---------------|--------------|
| >30           | Normal sale   |
| 10–30         | Discount      |
| <10           | Clearance     |
| Expired       | Remove        |

---

## Example Microservice Architecture

**Inventory Service Components:**
- Inventory API
- Stock Database
- Demand Analyzer
- Decision Engine (DMN)
- Workflow Engine (BPMN)
- Supplier Service
- Notification Service

**Technology Example:**
- Backend: Spring Boot
- Workflow: Camunda Platform 7
- Database: PostgreSQL
- Messaging: Kafka 
- Scheduler: Quartz

---

## Typical Inventory Flow

1. Sales Event
2. Inventory Service
3. BPMN Workflow
4. DMN Decision (Reorder / Demand / Season)
5. Supplier Order
6. Inventory Updated

---

