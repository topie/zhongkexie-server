ALTER TABLE d_score_answer
  ADD UNIQUE KEY upi(user_id, paper_id, item_id);

