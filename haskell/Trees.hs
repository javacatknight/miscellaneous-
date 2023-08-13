-- A data constructor is a "function" that takes 0 or more values and gives you back a new value.  
-- A type constructor is a "function" that takes 0 or more types and gives you back a new type.  
  
-- mathematical expressions  
-- leafs are numbers (integers)  
-- internal nodes are either unary functions with one child  
-- or binary functions with two children  
data MathTree  
  = Leaf Integer  
  | Unary (Integer -> Integer) MathTree  
  | Binary (Integer -> Integer -> Integer) MathTree MathTree  
  
-- This is here to let Haskell know how to display the MathTree to you.  
-- We will learn later what exactly this is and how it works.  
instance Show MathTree where  
  show (Leaf v) = show v  
  show (Unary f t) = "unary(" ++ show t ++ ")"  
  show (Binary f l r) = "binary(" ++ show l ++ "," ++ show r ++ ")"  
  
-- | eval t  
--  return the result of evaluating t  
eval :: MathTree -> Integer  
eval (Leaf x) = x  
eval (Unary f t) = f (eval t)  
eval (Binary f l r) = f (eval l) (eval r)  
  
-- | apply op t  
--  return the result of applying op to every leaf in t  
apply :: (Integer -> Integer) -> MathTree -> MathTree  
apply op (Leaf x) = Leaf (op x)  
apply op (Unary f t) = Unary f (apply op t)  
apply op (Binary f l r) = Binary f (apply op l) (apply op r)  
  
-- | element v t  
--  return whether v is in t  
element :: Integer -> MathTree -> Bool  
element v (Leaf x) = v == x  
element v (Unary f t) = element v t  
element v (Binary f l r) = element v l || element v r  
  
-- | replace v v' t  
--  return the result of replacing every value v in the leafs of t with v'  
replace :: Integer -> Integer -> MathTree -> MathTree  
replace v v' (Leaf x) = if v == x then Leaf v' else Leaf v  
replace v v' (Unary f t) = Unary f (replace v v' t)  
replace v v' (Binary f l r) = Binary f (replace v v' l) (replace v v' r)  
  
-- | sumLeafs t  
--  return the sum of the leafs of t  
sumLeafs :: MathTree -> Integer  
sumLeafs (Leaf x) = x  
sumLeafs (Unary _ t) = sumLeafs t  
sumLeafs (Binary _ l r) = sumLeafs l + sumLeafs r  
  
-- | numNodes t  
--  return the number of all nodes in t (including leaf and internal nodes)  
numNodes :: MathTree -> Int  
numNodes (Leaf _) = 1  
numNodes (Unary _ t) = 1 + numNodes t  
numNodes (Binary _ l r) = 1 + numNodes l + numNodes r  
  
-- | numLevels t  
--  return the number of levels in t (i.e., return the number of nodes on the longest root-to-leaf  
--  path in t)  
numLevels :: MathTree -> Int  
numLevels (Leaf _) = 1  
numLevels (Unary _ t) = 1 + numLevels t  
numLevels (Binary _ l r) = 1 + max (numLevels l) (numLevels r)  
  
-- | tflip t  
--  return a tree which is a result of swapping, for every binary node in the tree, its right and  
--  left child  
tflip :: MathTree -> MathTree  
tflip (Leaf x) = Leaf x  
tflip (Unary f t) = Unary f t  
tflip (Binary f l r) = Binary f (tflip r) (tflip l)  
