**Problem Restatement**
A dealer in Blackjack must keep drawing cards until their hand total is at least $(\text{target} - 4)$. For example, with a target of 21, the dealer continues drawing until their total is at least 17, then stands. If at any point the dealer’s total exceeds the target, they “bust.” Assuming an infinite deck where each card value (1 through 10) is equally likely ($P = 0.1$ each), compute the probability that the dealer will bust, given:

* `target`: the “bust” threshold (e.g. 21).
* `startingHand`: the dealer’s current total before drawing.

Return that probability rounded to three decimal places.

---

## Key Insights and Approach

1. **State Definition**
   Let `P(h)` = probability that the dealer eventually busts, assuming their current hand total is exactly `h`.

2. **Base Cases**

   * If `h > target`, they have already busted ⇒ `P(h) = 1.0`.
   * If `h ≥ target - 4` and `h ≤ target`, the dealer will stand (no more draws) ⇒ `P(h) = 0.0`.
     (Because from  `h` in $\(target - 4\), \(target\)$, drawing any card of value 1..10 would push them over or keep them under? Actually, by rule “stand’’ means “stop drawing once you reach $\ge target - 4$.” So at those `h` they do **not** draw more. Their chance of busting from that point is 0.)

3. **Recurrence**
   If `h < target - 4`, the dealer **must** draw. Each card value `c` ∈ {1..10} is drawn with probability 0.1. After drawing `c`, the new hand is `h + c`. Therefore:

   $$
     P(h)\;=\;\sum_{c=1}^{10} \bigl[\,0.1 \times P(h + c)\bigr].
   $$

4. **Memoization**
   Because many different draw‐sequences lead to the same “current hand total” `h`, we store already computed `P(h)` in a map. That way each `P(h)` is computed exactly once.

5. **Overall Probability**
   We want `P(startingHand)`. Finally, we round it to three decimal places.

6. **Complexity**

   * We may potentially compute `P(h)` for every `h` from `startingHand` up to at most `target + 10` (though in practice we clamp at base cases). That is $O(target - startingHand)$ different `h` values.
   * For each `h` we iterate over 10 possible card draws ⇒ $O(10) = O(1)$.
     ⇒ **Time**: $O\bigl((\text{target} - \text{startingHand}) \times 10\bigr) = O(\text{target} - \text{startingHand})$.
     ⇒ **Space**: $O(\text{target} - \text{startingHand})$ for the memo table.

---

## Complete Java Implementation

```java
package medium.recursion;

import java.util.HashMap;

public class BlackjackBustProbability {

  /**
   * Returns the probability (rounded to three decimals) that the dealer will bust,
   * given the target "bust" threshold and the dealer’s current hand total.
   *
   * @param target       The integer bust threshold (e.g., 21)
   * @param startingHand The dealer’s current hand total (must be ≤ target)
   * @return Probability of busting, rounded to three decimal places.
   */
  public static double calculateBustProbability(int target, int startingHand) {
    // Use a memo to store P(h) for each h
    HashMap<Integer, Double> memo = new HashMap<>();
    double rawProbability = probabilityHelper(target, startingHand, memo);

    // Round to three decimal places
    return Math.round(rawProbability * 1000.0) / 1000.0;
  }

  // Recursive helper that returns P(h) = probability of busting from hand = h
  private static double probabilityHelper(int target, int h, HashMap<Integer, Double> memo) {
    // 1) If h > target, the dealer has already busted:
    if (h > target) {
      return 1.0;
    }
    // 2) If h is in [target - 4, target], the dealer stands ⇒ 0 probability of busting
    //    (they stop drawing once they reach >= target - 4 but <= target).
    if (h >= target - 4) {
      return 0.0;
    }
    // 3) If we've computed P(h) before, return it
    if (memo.containsKey(h)) {
      return memo.get(h);
    }

    // 4) Otherwise, must draw one of cards 1..10 uniformly (prob = 0.1 each)
    double sumProb = 0.0;
    for (int card = 1; card <= 10; card++) {
      sumProb += 0.1 * probabilityHelper(target, h + card, memo);
    }

    // Store in memo and return
    memo.put(h, sumProb);
    return sumProb;
  }

  // Example usage
  public static void main(String[] args) {
    int target = 21;
    int startingHand = 15;
    double bustProb = calculateBustProbability(target, startingHand);
    System.out.printf(
        "Probability of busting with target=%d and startingHand=%d: %.3f%n",
        target, startingHand, bustProb
    );
    // Expected output: Probability of busting with target=21 and startingHand=15: 0.450
  }
}
```

---

## Explanation of Core Logic

1. **Base Cases**

   * `h > target` ⇒ Already over the target (bust) ⇒ Probability = 1.
   * `target - 4 ≤ h ≤ target` ⇒ Dealer stands (no more draws) ⇒ Probability = 0.

2. **Recursive Case**

   * If `h < target - 4`, the dealer must draw another card. Each card value $c$ ∈ {1…10} is equally likely ($P = 0.1$). After drawing $c$, the new hand is $h + c$. Thus

     $$
       P(h) \;=\; \sum_{c = 1}^{10} \bigl[\,0.1 \times P(h + c)\bigr].
     $$
   * We memoize these results in `memo` keyed by `h` so that each `P(h)` is computed only once.

3. **Rounding**

   * After computing `P(startingHand)` as a `double`, we do

     ```java
     Math.round(rawProbability * 1000.0) / 1000.0
     ```

     to produce a three‐decimal output (e.g.\ 0.450, 0.732, etc.).

---

## Complexity

* **Time Complexity**: $O\bigl((\text{target} - \text{startingHand}) \times 10\bigr) = O(\text{target} - \text{startingHand})$.
* **Space Complexity**: $O(\text{target} - \text{startingHand})$ for memoization.

This completes a memoized‐recursive solution for the dealer’s bust probability, returning a result rounded to three decimal places.
