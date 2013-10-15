-- Copyright © 2013 Bart Massey
-- Tabulate PNW Enlightened Logo votes
-- using Borda Count (or thereabouts).

import Data.Ord
import Data.List
import Data.Char
import Text.Printf

-- Strategy: Split the accumulator
-- at the index position; add the vote;
-- glue the accumulator back together.
acc :: [Int] -> (Char, Int) -> [Int]
acc a ('-', _) = a
acc a (c, v) =
  let i = ord c - ord 'a' in
  let (pre, target : post) = splitAt i a in
  pre ++ [target + v] ++ post

main :: IO ()
main = do
  voteText <- getContents
  -- Strategy: Label each vote with 
  -- its count; jam them together,
  -- use a foldl' to accumulate votes.
  -- Finally, sort the candidates in descending vote
  -- count order and print.
  let votes = sortBy (comparing (Down . snd)) $
              zip ['a' ..] $
              foldl' acc (replicate 6 0) $
              concatMap (flip zip [6,5..1]) $
              lines voteText
  mapM_ (\(c, v) -> printf "%c: %d\n" c v) votes
