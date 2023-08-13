/*
Basic implementation of AVL trees, a kind of balanced binary tree. Struct types are ommitted.
*/


/*************************************************************************
 ** Suggested helper functions
 *************************************************************************/

/* Returns the height (number of nodes on the longest root-to-leaf path) of
 * the tree rooted at node 'node'. Returns 0 if 'node' is NULL.
 */
int height(AVL_Node* node){
  if (node==NULL)
    return 0;
  
  int x = height(node->left);
  int y = height(node->right);

  if (x<y)
    return y+1;
  return x+1;
}

/* Updates the height of the tree rooted at node 'node' based on the heights
 * of its children. Note: this should be an O(1) operation.
 */
void update_height(AVL_Node* node){
  return height(node) +1;
  //(node->left->height < node->right->height) ? (node->height = 1 + node->right->height) : (node->height = 1 + node->left->height);
}

/* Returns the balance factor (height of left subtree - height of right
 * subtree) of node 'node'. Returns 0 of node is NULL.
 */
int balance_factor(AVL_Node* node){
  if (node == NULL)
    return 0;
  return height(node->left) - height(node->right); 
}

/* Returns the result of performing the corresponding rotation in the AVL
 * tree rooted at 'node'.
 */
// single rotations: right/clockwise
AVL_Node* right_rotation(AVL_Node* node) {
  AVL_Node* x = node->left;
  node->left = x->right;
  x->right = node;
  return x;
}
// single rotations: left/counter-clockwise
AVL_Node* left_rotation(AVL_Node* node){
  AVL_Node* x = node->right;
  node->right = x->left;
  x->left = node;
  return x;
}
// double rotation: right/clockwise then left/counter-clockwise
AVL_Node* right_left_rotation(AVL_Node* node){
  node->right = right_rotation(node->right);
  return left_rotation(node);
}
// double rotation: left/counter-clockwise then right/clockwise
AVL_Node* left_right_rotation(AVL_Node* node){
  node->left - left_rotation(node->left);
  return right_rotation(node);
}

/* Returns the successor */
AVL_Node* successor(AVL_Node* node){
  //Precondition, node has two children
  AVL_Node* tmp = node->right;
  while (tmp->left != NULL){
    tmp = tmp->left;
  }
  return tmp;
}
/* Creates and returns an AVL tree node with key 'key', value 'value', height
 * of 1, and left and right subtrees NULL.
 */
AVL_Node* create_node(int key, void* value){
  AVL_Node* x = (AVL_Node*) malloc(sizeof(AVL_Node));
  x->key = key;
  x->value = value;
  x->height = 1;
  x->left = NULL;
  x->right = NULL;
  return x;
}


void delete_tree(AVL_Node* node) {
  if (node == NULL) return;
  delete_tree(node->left);
  delete_tree(node->right);
  free(node);
}

/*************************************************************************
 ** Required functions
 ** Must run in O(log n) where n is the number of nodes in a tree rooted
 **  at 'node'.
 *************************************************************************/


AVL_Node* rebalanceRight(AVL_Node* node){
  if (balance_factor(node) <-1){
    AVL_Node* x = node->right;
    if (height(x->left) <= height(x->right)){
      return left_rotation(node);
    } else {
      return right_left_rotation(node);
    }
  } else {
    return node;
  }
}

AVL_Node* rebalanceLeft(AVL_Node* node){
  if (balance_factor(node) >1){
    AVL_Node* x = node->left;
    if (height(x->right) <= height(x->left)){
      return right_rotation(node);
    } else {
      return left_right_rotation(node);
    }
  } else {
    return node;
  }
}

AVL_Node* search(AVL_Node* node, int key) {
  if (node == NULL)
    return NULL;
  
  if (key< node->key) {
    search (node->left, key);
  } else if (key == node->key) {
    return node;
  } else {
    search(node->right, key);
  }

  //not found, all the branches return NULL
}

AVL_Node* insert(AVL_Node* node, int key, void* value) {
  if (node == NULL){
    return create_node(key, value);
  }

  if (key == node->key){
    node->value = value;
  } else if (key<node->key){
    node->left = insert(node->left, key, value);
    node = rebalanceLeft(node);
    update_height(node);
  } else {
    node->right = insert(node->right, key, value);
    node = rebalanceRight(node);
    update_height(node);
  }
 
  return node;
}

AVL_Node* delete(AVL_Node* node, int key) {
  if (node == NULL){
    return NULL;
  }

  if (key == node->key){
    //children cases
    AVL_Node* tmp;
    if (node->left == NULL && node->right == NULL){
      delete_tree(node);

    } else if (node->left == NULL) {
      tmp = node->right;
      free(node);
      return tmp;

    } else if (node->right == NULL) {
      tmp = node->left;
      free(node);
      return tmp;

    } else {
      tmp = successor(node);
      //you can do two searches (with deleting one calling the delete of another)
      //or slightly faster is getting the parent of the successor, special case if the successor is the right of the node
      node->key = tmp->left->key;
      node->value = tmp->left->value; 
      //do a tmp->left! = null check? 
      //height does not change, left right does not change.
      delete_tree(tmp);
      tmp->left = tmp->left->right; 
      AVL_Node* create_node(int key, void* value){
  AVL_Node* x = (AVL_Node*) malloc(sizeof(AVL_Node));
  x->key = key;
  x->value = value;
  x->height = 1;
  x->left = NULL;
  x->right = NULL;
  return x;
}

    }
  } else if (key<node->key){
    node->left = delete(node->left, key);
    node = rebalanceLeft(node);
    update_height(node);
  } else {
    node->right = delete(node->right, key);
    node = rebalanceRight(node);
    update_height(node);
  }

  return node;
}
