-- | interleave xs ys  
--  return a list of corresponding elements from xs and ys, interleaved. The remaining elements of  
--  the longer list are ignored.  This function should use recursion and pattern matching.  
  
interleave :: [a] -> [a] -> [a]  
interleave (x : xs) (y : ys) = x : y : interleave xs ys  
interleave [] _ = []   
interleave _ [] = []  
  
  
-- | toPairs xs ys  
--  return a list of pairs of corresponding elements from xs and ys, interleaved. The remaining  
--  elements of the longer list are ignored.  This function should use recursion and pattern  
--  matching.  
toPairs :: [a] -> [b] -> [(a, b)]  
toPairs (x : xs) (y : ys) = (x, y) : toPairs xs ys  
toPairs _ [] = []  
toPairs [] _ = []  
  
-- | repeat' f x n  
--  return a list [x, f(x), f(f(x)), ..., f^n(x)]  
--  requires n >= 0  
--  This function should use map, recursion, and pattern matching.  
repeat' :: (a -> a) -> a -> Int -> [a]  
repeat' f x n = x : map (\i -> f (last (repeat' f x (i - 1)))) [1 .. n]  
  
-- i = 0 => x  
-- i = 1 => f last(repeat f x i) => f x  
-- i = 2 => f  
-- repeat f x 0 = [x]  
-- rpeeat f x 1 = [x, f(x)]  
-- repeat f x 2 = [x, f(x), f(fx)]  
  
-- | numNeg xs  
--  return a number of negative elements in xs  
--  No recursion, no higher-order functions. Use list comprehension!  
numNeg :: (Ord a, Num a) => [a] -> Int  
numNeg xs = sum ([1 | a <- xs, a < 0])  
  
-- recursive: if ((head xs) < 0) then 1 + numNeg (tail xs) else numNeg ( tail xs)  
-- numNeg [] = 0  
  
-- | genSquares low high  
--  return a list of squares of even integers between low and high, inclusive.  
--  No recursion, no higher-order functions. Use list comprehension!  
genSquares :: Int -> Int -> [Int]  
genSquares low high = [x * x | x <- [low..high]]  
  
-- | triples n  
--  return a list of triples (x,y,z) of positive numbers all less than  
--  or equal to n, such that x^2 + y^2 == z^2, with no duplicate triples or  
--  premutations of ealier triples.  
--  No recursion, no higher-order functions. Use list comprehension!  
triples :: Int -> [(Int, Int, Int)]  
triples n =  
  [(x, y, z) | x <- [1 .. n],y <- [x .. n], z <- [y + 1 .. n], x^2 + y^2 == z^2] 
