Below are structured, consolidated **revision notes** for Chapter 2 (“Back-of-the-Envelope Estimation”). Treat this as your “cheat sheet” when preparing for interviews—everything is labeled, abbreviated minimally, and organized so you can quickly recall and apply each concept.

---

# 1. What Is “Back-of-the-Envelope” Estimation?

* **Definition (Jeff Dean):**
  “Estimates you create using thought experiments + common performance numbers to gauge which designs will meet requirements.”

* **Interview Goal:**

  1. **State assumptions clearly.**
  2. **Round aggressively** (no exact decimals).
  3. **Label units** (bytes vs. KB vs. MB; ns vs. µs vs. ms).
  4. **Use rule-of-thumb values** (latency, read/write ratios, etc.).
  5. **Speak your process** as you calculate—interviewers care more about approach than perfect accuracy.

---

# 2. Power of Two (Data Volume Units)

> **Key Idea:** Computers store data in powers of 2. If you confuse 1 GB = 10⁹ B vs. 2³⁰ B (≈1.07×10⁹ B), you incur a \~7 % error.

### 2.1 Binary vs. Decimal Conventions

| Binary Unit | Exact Bytes (2ⁿ)            | Approx. Decimal Equivalent | Common Symbol Usage |
| :---------: | :-------------------------- | :------------------------- | :------------------ |
|    1 KiB    | 2¹⁰ B = 1,024 B             | \~1.0×10³ B                | “1 KB” (common)     |
|    1 MiB    | 2²⁰ B = 1,048,576 B         | \~1.0×10⁶ B                | “1 MB”              |
|    1 GiB    | 2³⁰ B = 1,073,741,824 B     | \~1.0×10⁹ B                | “1 GB”              |
|    1 TiB    | 2⁴⁰ B = 1,099,511,627,776 B | \~1.0×10¹² B               | “1 TB”              |
|    1 PiB    | 2⁵⁰ B ≈ 1.1259×10¹⁵ B       | \~1.0×10¹⁵ B               | “1 PB”              |

* **Practice Tip:**

  * When doing rough math, you can approximate “1 GB ≈ 10⁹ B” or “1 MiB ≈ 10⁶ B.”
  * Always write “≈” or “\~” if you round (e.g., 1 GiB ≈ 1 × 10⁹ B).

### 2.2 Why It Matters

1. **Storage Costs / Scale:** If you need 55 PB of media, saying “55 × 2⁵⁰ B” vs. “55 × 10¹⁵ B” can shift infrastructure decisions (e.g., cloud object store vs. self-host).
2. **Memory vs. Disk Sizing:** RAM modules and SSD drives are sold in binary increments (16 GiB, 256 GiB, 1 TiB). If you design for “180 GB,” actually choose “256 GiB.”
3. **Network Transfer Units:** Under the hood, certain networking equipment measures in binary (MTU ≈ 1,500 B, but alignment to 1 KiB boundaries matters).

---

# 3. Latency Numbers Every Programmer Should Know

> **Purpose:** Understand orders of magnitude—so you can decide whether to place data in RAM, cache, or disk; whether to batch I/O; or if an extra network hop is too slow.

### 3.1 Time-Unit Conversions

* 1 ns = 10⁻⁹ s
* 1 µs = 10⁻⁶ s = 1,000 ns
* 1 ms = 10⁻³ s = 1,000 µs = 1,000,000 ns

### 3.2 Typical Latencies (2020-Era)

| Operation Type                                         | Latency (Approximate)               |
| :----------------------------------------------------- | :---------------------------------- |
| **L1 CPU Cache Access**                                | \~1 – 2 ns                          |
| **L2/L3 CPU Cache Access**                             | \~5 – 15 ns                         |
| **Main Memory (DRAM) Access**                          | \~50 – 100 ns                       |
| **Redis/Memcached (in-memory get)**                    | \~50 ns                             |
| **NVMe SSD Random Read (4 KB block)**                  | \~20 – 100 µs (20,000 – 100,000 ns) |
| **HDD Seek + Rotational Latency**                      | \~8 – 10 ms (8×10⁶–10×10⁶ ns)       |
| **Sequential Disk Read/Write (large block)**           | \~100 MB/s → \~10 ms/GB             |
| **Network RTT (within same data center)**              | \~0.5 – 1 ms (500 – 1,000 µs)       |
| **Network RTT (cross-continent/region)**               | \~50 – 150 ms (50,000 – 150,000 µs) |
| **Disk Write (synchronous fsync)**                     | \~1 – 2 ms                          |
| **L1 Branch Mispredict**                               | \~5 ns                              |
| **Local Inter-Process Call (loopback TCP, same host)** | \~10–50 µs                          |

#### 3.2.1 Key Takeaways

1. **Memory ≫ Disk:**

   * DRAM (\~50 ns) is \~10⁵× faster than HDD (\~5 ms).
   * Even NVMe SSD (\~50 µs) is \~1,000× slower than DRAM.

2. **Avoid Random Disk Seeks:**

   * A single random read (50 µs) × 1,000 → \~50 ms. Better to batch reads sequentially.

3. **Compress Before Network:**

   * Simple compression (LZ4) takes \~1 ms per MB.
   * If your network transfer for 1 MB is \~10 ms, compressing to 0.5 MB saves \~5 ms net.
   * Always weigh CPU cost vs. network time (especially for cross-region transfers).

4. **Cross-Region Calls Are Costly:**

   * A 100 ms round-trip is 2,000× a local RAM access and 100× a data-center-only network call.
   * Minimize cross-region synchronous calls; use async messages or local caches.

---

# 4. Availability & “Number of Nines”

> **High Availability:** % of time a system remains operational. Expressed in “nines.” The more nines, the less downtime.

### 4.1 “Nines” Table (Yearly and Monthly Downtime)

|      Availability      | Annual Downtime | Monthly Downtime (≈30 days) |
| :--------------------: | :-------------: | :-------------------------: |
|   **90.0% (1 nine)**   |   \~36.5 days   |           \~3 days          |
|   **99.0% (2 nines)**  |   \~3.65 days   |          \~7 hours          |
|   **99.9% (3 nines)**  |   \~8.76 hours  |          \~43.8 min         |
|  **99.99% (4 nines)**  |    \~52.6 min   |          \~4.38 min         |
|  **99.999% (5 nines)** |    \~5.26 min   |           \~26 sec          |
| **99.9999% (6 nines)** |    \~31.6 sec   |          \~2.63 sec         |

* **SLA Examples:**

  * AWS EC2: 99.99%
  * Google Compute Engine: 99.95% (or higher tiers)
  * Azure VMs: 99.9%–99.99%

#### 4.1.1 Usage in Interviews

1. **If asked, “We need 99.99% uptime—what does that mean?”**

   * Explain: 99.99% → \~52 minutes downtime in a year (≈4 minutes/month).
   * Therefore: design multi-AZ failover, auto-healing, rolling upgrades without downtime.

2. **“What architecture gets us from 99.9% to 99.99%?”**

   * Single-AZ MySQL replication can give \~99.9%.
   * Multi-AZ MySQL + automated failover → \~99.99%.
   * Going beyond to 99.999% requires multi-region, multi-master setups, self-healing networks.

3. **Trade-Offs:**

   * More nines → exponentially more cost/complexity. Evaluate ROI vs. business criticality.

---

# 5. Example Walkthrough: Twitter-Like Service Estimation

> **Scenario Prompt (Example):**
> “Estimate peak QPS and 5-year storage requirements (text + media) for a Twitter-scale app with 300 M MAU.”
>
> **Key Steps:**
>
> 1. State assumptions.
> 2. Compute DAU, tweets/day.
> 3. Convert to TPS (average), then to peak TPS.
> 4. Estimate read QPS (using read/write ratio).
> 5. Calculate daily + multi-year storage for media and text.
> 6. Map those numbers to servers, cache, DB, and storage infrastructure.

---

### 5.1 Step 1: List & Label Your Assumptions

1. **Monthly Active Users (MAU):** 300 million (3 × 10⁸)
2. **Daily Active Users (DAU):** 50% of MAU → 150 million per day
3. **Tweets per Active User per Day:** 2
4. **% Tweets with Media:** 10%
5. **Text Size Per Tweet:**

   * tweet\_id = 64 B
   * text = 140 B
   * → total ≈ 204 B/tweet
6. **Media Size Per Tweet:** \~1 MB (1 × 10⁶ B) average
7. **Media Retention Period:** 5 years
8. **Units Convention:**

   * Use “1 MB” = 1 × 10⁶ bytes (decimal) for simplicity.
   * Use “1 GB” = 1 × 10⁹ bytes (decimal).

> **Tip:** Write them on the board as bullet points. If any assumption changes, redo the math with minimal adjustments.

---

### 5.2 Step 2: Compute Write QPS (TPS)

1. **DAU =** 150 million
2. **Tweets/day =** 150 M × 2 = 300 M tweets/day
3. **Seconds per day =** 24 h × 3,600 s/h = 86,400 s/day
4. **Average TPS (writes) =** 300 M / 86,400 ≈ 3,472 ≈ 3.5 k tweets/s
5. **Peak TPS ≈** 2× average → 3.5 k TPS × 2 = \~7 k TPS

> **Rationale:** Traffic is not uniform; mornings/evenings spike. A 2× factor is a reasonable rule-of-thumb.

---

### 5.3 Step 3: Estimate Read QPS

* **Rule-of-Thumb Read/Write Ratio:** \~10:1 (common for social feed applications).
* **Peak Write TPS =** 7 k TPS → **Peak Read QPS =** 7 k × 10 = **70 k QPS**

> **Why 10:1?** Because every new tweet is read many times: user’s followers, feed refreshes, search, etc.

---

### 5.4 Step 4: Calculate Storage Requirements

#### 5.4.1 Media Storage

1. **Media Tweets/day =** 300 M × 10% = **30 M media tweets/day**
2. **Media/day (bytes) =** 30 M × 1 MB = 30 × 10⁶ MB = 30 × 10³ GB = **30 TB/day**
3. **Days in 5 years ≈** 5 × 365 = 1,825 days
4. **5-Year Total Media =** 30 TB/day × 1,825 days = **54,750 TB ≈ 55 PB**

> **Label:** “\~55 PB of raw media storage over 5 years (ignore compression/dedup).”

#### 5.4.2 Text Storage

1. **Text size/tweet ≈** 204 B
2. **Total text/day =** 300 M × 200 B ≈ 60 × 10⁹ B = **60 GB/day**
3. **5-Year Text =** 60 GB/day × 1,825 days ≈ **109,500 GB ≈ 110 TB**

> **Comparison:** 110 TB (text) vs. 55 PB (media). Clearly, media dwarfs text volume.

---

### 5.5 Step 5: Derive High-Level Infrastructure “Buckets”

1. **Application (Web/API) Tier:**

   * **Peak Write TPS:** \~7 k/s
   * **Peak Read QPS:** \~70 k/s
   * **Assume** each app server handles \~2 k read QPS + \~200 write QPS (moderate business logic + DB/cache lookups).

     * **Write Servers Needed:** 7 k / 200 ≈ 35 servers
     * **Read Servers Needed:** 70 k / 2 k ≈ 35 servers
   * **Round Up + Buffer:** Deploy \~50–70 app servers (behind LB) to handle spikes and rolling upgrades.

2. **Caching Layer (Redis/Memcached):**

   * **Target Hit Ratio:** 90% of reads served from cache.
   * **Peak Read QPS to Cache =** 0.9 × 70 k = 63 k QPS.
   * **Cache Working Set Size:**

     * If caching \~100 million tweets at \~1 KB each → 100 GB total.
     * Accounting for replication + overhead → provision \~200 GB Redis cluster (sharded).

3. **Database Tier (SQL/NoSQL):**

   * **After Cache Misses:** \~10% of 70 k = 7 k read QPS + 7 k write QPS = 14 k DB QPS.
   * **Single MySQL** → \~5–10 k QPS max (depends on hardware, indexing, query complexity).
   * **Options:**

     * **Sharded MySQL:** Partition by user\_id (e.g., 10–20 shards); each shard handles \~1.4 k QPS.
     * **Write-Optimized NoSQL (Cassandra):** Single cluster can handle 14 k QPS easily, with eventual consistency.
   * **Replication Factor:** 3× for durability → raw data footprint ×3.

4. **Media Storage:**

   * **Total Raw:** \~55 PB over 5 years.
   * **Using Cloud Object Store (e.g., S3):** Pay per GB, automatic replication, lifecycle policies to archive cold data.
   * **If Self-Hosted:**

     * Must allocate \~55 PB raw + replication/durability overhead → \~110 PB hardware.
     * Realistic only for hyperscalers; most teams use a managed object store.

5. **Network / Bandwidth Requirements:**

   * **Read Responses:** If each JSON response with metadata \~50 KB, at 70 k QPS → 70 k × 50 KB = 3.5 GB/s ≈ 28 Gb/s outgoing.
   * **Media Uploads:** 30 M media tweets/day = 30 TB/day = 347 MB/s average inbound. Account for peaks → provision ≥1 Gb/s inbound per region.

6. **Autoscaling & Monitoring:**

   * **App Tier:** Autoscale web servers if CPU > 60% or QPS > threshold.
   * **Cache Tier:** Scale out Redis if memory > 80% or CPU > 70% or network saturation.
   * **DB Tier:** Add replicas if replication lag > 2 s or CPU > 70%.
   * **Media Storage:** Lifecycle policies to move older objects to cold storage.
   * **Monitoring Metrics:**

     * QPS, latency p50/p95/p99, error rates.
     * Cache hit ratio, eviction rate.
     * Disk IOPS, replication lag.
     * Bandwidth usage, network errors.

---

# 6. Best Practices & Pro Tips

1. **Always Write (and Speak) Your Assumptions:**

   * If DAU changes, recalc DAU first. If tweet size changes, recalc storage next.
   * Interviewers respect clarity: e.g., “I assume 50% of MAU are daily active, each posts 2 tweets/day, etc.”

2. **Round Early & Often (Order-of-Magnitude Thinking):**

   * Instead of 3,472 TPS, say “\~3.5 k TPS → design for \~7 k TPS.”
   * 54,750 TB → “\~55 PB.”
   * 60 GB/day of text → “\~0.1 PB over 5 years.”

3. **Label Units Relentlessly:**

   * “We need \~30 TB/day of media.”
   * “That’s \~55 PB over 5 years, ignoring compression.”
   * “We serve \~70 k read QPS; if each response is \~50 KB, that’s \~3.5 GB/s.”

4. **Include a Safety Buffer (×1.2–×2):**

   * If you compute “35 servers,” propose “50 servers” to handle spikes + maintenance.
   * If you calculate “30 TB/day,” plan for “40 TB/day” to accommodate occasional hot days.

5. **Use “Rule-of-Thumb” Ratios:**

   * **Read/Write Ratio ≈ 10:1** for social “feed” workloads.
   * **Cache Hit Ratio ≈ 80%–95%** depending on use case (adjust if domain suggests differently).
   * **Peak Factor ≈ ×2–×3** over average TPS (adjust if daily usage graph is steeper).

6. **Zero In on Bottlenecks:**

   * If disk IO is the slowest (ms), cache more aggressively.
   * If cross-region RTT (50–150 ms) is too high, consider local caching or multi-region replication.

7. **Explain Trade-Offs Alongside Numbers:**

   * “Using a single MySQL instance can’t handle 14 k QPS—would introduce sharding complexity. A write-optimized NoSQL cluster simplifies scaling at the cost of weaker consistency.”
   * “Storing 55 PB on-prem costs hundreds of millions; using S3 with lifecycle policies is far cheaper and automatically scaled.”

8. **Practice Common Estimation Scenarios:**

   * Social feed QPS & storage
   * Video-streaming bandwidth & storage
   * IoT telemetry ingestion rates
   * Search indexing throughput & disk usage

---

# 7. Consolidated “On-Demand” Cheat Sheet

Use this when you need a **quick mental checklist** during an interview:

### A. Units & Conversions

* **Binary Conversions:**

  * 1 KiB = 2¹⁰ B ≈ 1.0 × 10³ B
  * 1 MiB = 2²⁰ B ≈ 1.0 × 10⁶ B
  * 1 GiB = 2³⁰ B ≈ 1.0 × 10⁹ B
  * 1 TiB = 2⁴⁰ B ≈ 1.0 × 10¹² B
  * 1 PiB = 2⁵⁰ B ≈ 1.0 × 10¹⁵ B

* **Time Conversions:**

  * 1 ns = 10⁻⁹ s
  * 1 µs = 10⁻⁶ s (1,000 ns)
  * 1 ms = 10⁻³ s (1,000 µs = 1,000,000 ns)

### B. Latency Reference (Order-of-Magnitude)

| Operation                              | Latency     |
| :------------------------------------- | :---------- |
| L1 Cache                               | \~1 ns      |
| L2/L3 Cache                            | \~5–15 ns   |
| DRAM (Main Memory)                     | \~50–100 ns |
| Redis/Memcached (in-memory key lookup) | \~50 ns     |
| NVMe SSD random read (4 KB)            | \~20–100 µs |
| HDD random seek + rotational latency   | \~8–10 ms   |
| Sequential Disk Read/Write (1 GB)      | \~10–20 ms  |
| Network RTT (same DC)                  | \~0.5–1 ms  |
| Network RTT (cross-continent)          | \~50–150 ms |
| Disk sync (fsync)                      | \~1–2 ms    |
| Function Call (in-process)             | \~5–10 ns   |
| Branch Mispredict                      | \~5 ns      |

> **Interpretation:**
>
> * **In-memory ≪ SSD ≪ HDD.**
> * **DC network ≪ cross-region network.**

### C. Availability “Nines”

| Availability | Annual Downtime | Monthly Downtime |
| :----------: | :-------------: | :--------------: |
|     90.0%    |   \~36.5 days   |     \~3 days     |
|     99.0%    |   \~3.65 days   |     \~7 hours    |
|     99.9%    |   \~8.76 hours  |  \~43.8 minutes  |
|    99.99%    |  \~52.6 minutes |  \~4.38 minutes  |
|    99.999%   |  \~5.26 minutes |   \~26 seconds   |
|   99.9999%   |  \~31.6 seconds |   \~2.6 seconds  |

> **Design Implications:**
>
> * 99.9% (3 nines) → \~9 hours downtime/yr → single-AZ with standard replication can suffice.
> * 99.99% (4 nines) → \~52 minutes downtime/yr → multi-AZ automated failover.
> * 99.999% (5 nines) → \~5 minutes downtime/yr → multi-region, multi-master, self-healing.

### D. Quick Twitter-Style Estimation Workflow

1. **List Assumptions (Label Units):**

   * MAU = 300 M
   * DAU = 50% → 150 M/day
   * Tweets/DAU/day = 2
   * Media fraction = 10%
   * Text size ≈ 200 B/tweet; media ≈ 1 MB/tweet
   * Retention = 5 years

2. **Compute Write QPS:**

   * Tweets/day = 150 M × 2 = 300 M
   * Seconds/day = 86,400
   * Avg TPS = 300 M / 86,400 ≈ 3.5 k TPS
   * Peak TPS ≈ 2 × 3.5 k = 7 k TPS

3. **Estimate Read QPS:**

   * Read/Write ratio \~10:1 → Peak read QPS = 7 k × 10 = 70 k QPS

4. **Calculate Media Storage:**

   * Media tweets/day = 300 M × 0.10 = 30 M
   * Media/day = 30 M × 1 MB = 30 TB/day
   * 5 years ≈ 1,825 days → 30 TB × 1,825 ≈ 54,750 TB ≈ 55 PB

5. **Calculate Text Storage:**

   * Text/day = 300 M × 200 B ≈ 60 GB/day
   * 5 years → 60 GB × 1,825 ≈ 109,500 GB ≈ 110 TB

6. **Map to Hardware:**

   * App servers: assume 2 k read + 200 write QPS/server → need \~35 write-bias + \~35 read-bias servers → round to \~50–70 total.
   * Cache: 90% hit ratio → 63 k reads/s in cache → if caching 100 M tweets at 1 KB = 100 GB, plus replication → \~200 GB Redis cluster.
   * DB: forgive 10% cache miss → 7 k read + 7 k write = 14 k QPS. MySQL can’t handle that single-node → either shard (10–20 shards) or use Cassandra.
   * Media storage: 55 PB → use cloud object store (S3); self-host only if you’re scaling at hyperscale.
   * Network: 70 k QPS read × 50 KB/response ≈ 3.5 GB/s → \~28 Gb/s. Plan >= 30 Gb/s outbound.

---

# 8. Top Tips for Interview Success

1. **Speak Your Assumptions:**

   * “I assume 50% MAU is DAU, 2 tweets/day, 10% media, 1 MB media size, etc.”

2. **Round & Label Units:**

   * “300 M tweets/day → 300e6 / 86,400 ≈ 3.5 k TPS → peak \~7 k TPS.”
   * “30 M media/day × 1 MB = 30 TB/day; 30 TB × 1,825 = 55 PB/5 years.”

3. **Use Buffers (×1.2–×2):**

   * If “we need 35 servers,” propose “50 servers” for safety/hot-spare.
   * If “we need 30 TB/day,” plan “40 TB/day” to cover bursts.

4. **Know Your Rule-of-Thumbs:**

   * **Read/Write ≈ 10:1** for social feeds.
   * **Cache Hit ≈ 80–95%** depending on domain.
   * **Peak Factor ≈ 2×** average.

5. **Recall Latency Orders-of-Magnitude:**

   * DRAM (\~50 ns) ≪ SSD (\~50 µs) ≪ HDD (\~10 ms) ≪ DC network (\~1 ms) ≪ cross-region (\~100 ms).

6. **Availability & Nines:**

   * 99.9% → \~9 h downtime/yr; 99.99% → \~52 min/yr; 99.999% → \~5 min/yr.
   * Choose architecture accordingly (single-AZ vs. multi-AZ vs. multi-region).

7. **Always Relate Numbers to Architecture:**

   * If you need 7 k TPS writes, CPU + DB must support that.
   * 55 PB storage → cloud object store + lifecycle policies.
   * 70 k read QPS → aggressive caching + global CDN.

8. **Practice, Practice, Practice:**

   * Run through 3–5 “common” back-of-the-envelope scenarios:

     1. Social feed (tweets + media).
     2. Video streaming service (bandwidth + storage).
     3. IoT telemetry ingestion (events/s + retention).
     4. Search index (docs/day + index size).
     5. E-commerce checkout (orders/s + catalog size).

---

> **Final Note:**
> Keep these bullets handy. In your next interview, when asked to estimate capacity or throughput, simply walk through Steps 1–6, citing the relevant power-of-two conventions, latency rules, and availability trade-offs. As long as you clearly label units, round logically, and justify each assumption, you’ll nail every back-of-the-envelope question. Good luck!
