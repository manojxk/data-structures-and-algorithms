**Chapter 3: A Framework for System Design Interviews (Concise Notes)**

---

### 1. Why System Design Interviews Matter

* **Purpose:** Simulate solving an ambiguous, open-ended problem collaboratively.
* **What Interviewers Assess:**

  * Collaboration & communication (ask good questions, respond to feedback)
  * Trade-off awareness (avoid over-engineering)
  * Ability to handle ambiguity and pressure
  * No “perfect” answer—process is more important than final design.

---

### 2. Four-Step Framework

#### Step 1 – Understand the Problem & Scope (3–10 min)

1. **Pause & Clarify** before designing.
2. **Ask Questions** to nail down:

   * Required features (e.g., “post + feed”, “media support,” “sorting order”)
   * User base & scale (DAU, expected growth)
   * Data types (text only, images/videos)
   * Performance & availability targets
   * Existing tech/services to leverage
3. **Restate Requirements** to confirm alignment.
4. **Record Assumptions** on the board if interviewer asks you to.

---

#### Step 2 – High-Level Design & Buy-In (10–15 min)

1. **Draw Big-Box Diagram** showing:

   * Clients → Load Balancer/API Gateway → Web/API Servers
   * Cache Layer ↔ Primary Data Store (SQL/NoSQL)
   * Object Storage/CDN for static assets
   * Optional: Message Queue for async tasks, Logging/Monitoring
2. **Explain Data Flow** in two broad phases (e.g., “Publish Flow” vs. “Read/Fetch Flow”).
3. **Mention Trade-Offs** (e.g., push vs. pull feed, SQL vs. NoSQL, caching TTL).
4. **Optional Back-of-the-Envelope Estimates** (QPS, storage, server count) to check feasibility.
5. **Get Interviewer Feedback** (“Does this high-level approach make sense?”).

---

#### Step 3 – Deep Dive Into Key Components (10–25 min)

1. **Identify Bottlenecks** (based on scale, hints from interviewer):

   * High-fan-out write (e.g., “push each new post to thousands of feeds”)
   * Read-heavy feed retrieval (cache design, pagination)
   * Database sharding/partitioning strategies
2. **Drill on 1–2 Critical Pieces**:

   * **Queue Design & Worker Pool** for asynchronous fan-out
   * **Cache Sharding & Invalidation** for per-user feed stores
   * **Partitioning Key / Sharding Logic** for large tables (userID % N shards)
3. **Discuss Trade-Offs & Alternatives**:

   * Push (precompute feeds) vs. pull (compute on read) for “hot” users
   * Strong vs. eventual consistency for multi-region replication
4. **Avoid Unnecessary Low-Level Detail** unless prompted (no full SQL schema up front).

---

#### Step 4 – Wrap Up & Next Steps (3–5 min)

1. **Recap the Design** in 2–3 sentences:

   * E.g., “Clients → LB → API servers; writes go to Post DB + Fan-Out queue; workers push into Feed Cache; reads hit Feed Cache with DB fallback.”
2. **Highlight Bottlenecks & Solutions**:

   * “Fan-out queue lag → autoscaled workers; cache eviction → scale shards or extend TTL.”
3. **Operational Considerations**:

   * Monitoring: QPS, p99 latency, queue depth, cache hit ratio, DB lag
   * Logging: errors in fan-out, cache misses, API 5xx rates
   * Deployment: blue-green or canary releases; schema migrations with minimal downtime
4. **Future Scaling**:

   * If DAU × 10: add more queue partitions, shard cache, deploy multi-region for lower latency
5. **Invite Feedback**:

   * “Anything you’d like me to elaborate on or adjust?”

---

### 3. Dos & Don’ts

**Dos**

* Ask clarifying questions before diving in.
* Restate requirements to confirm understanding.
* Sketch a high-level blueprint first; then refine.
* Perform rough QPS/storage “order-of-magnitude” estimates.
* Drill only into the most critical components.
* Treat the interviewer as a teammate; seek “buy-in.”
* Discuss operational aspects: monitoring, alerting, autoscaling.
* Invite feedback and be ready to adapt.

**Don’ts**

* Don’t jump to a solution without clarifying.
* Don’t over-engineer or optimize prematurely.
* Don’t spend all your time on one subcomponent.
* Don’t dive into low-level code/schema unless asked.
* Don’t think in silence—communicate your reasoning.
* Don’t declare your design “perfect”—always note improvements.

---

### 4. Rough Time Allocation (45 min)

| Step                                   | Approx. Time |
| -------------------------------------- | ------------ |
| **1. Understand Requirements & Scope** | 3–10 min     |
| **2. High-Level Design & Buy-In**      | 10–15 min    |
| **3. Deep Dive Into Key Component(s)** | 10–25 min    |
| **4. Wrap Up & Next Steps**            | 3–5 min      |

> **Tip:** If time is short, signal it: “We have \~5 min left—would you prefer a summary or deeper dive into caching?”

---

Use these concise notes to rehearse your problem-scoping, high-level sketch, focused deep dives, and structured wrap-up. Good luck!
