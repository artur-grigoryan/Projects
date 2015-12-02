package binaryTree;

import java.util.ArrayList;


public class Node {
	int data;
	Node left;
	Node right;
	Node(int d){
		data=d;
		left=null;
		right=null;
	}
	public static Node insert(Node root, int d){
		Node n=new Node(d);
		if(root==null){
			root=n;
		}
		else{
			if(d<root.data){
				root.left=insert(root.left, d);
			}
			else{
				root.right=insert(root.right, d);
			}
		}
		return root;
	}
	
	public static void print(Node n){
		System.out.print(n.data+" ");
	}
	
	public static void inorder(Node root){
		if(root!=null){
			inorder(root.left);
			print(root);
			inorder(root.right);
		}
	}
	
	public static void preorder(Node root){
		if(root!=null){
			print(root);
			preorder(root.left);
			preorder(root.right);
		}
	}
	
	public static void postorder(Node root){
		if(root!=null){
			postorder(root.left);
			postorder(root.right);
			print(root);
		}
	}
	
	public static int maxDepth(Node root){
		if(root==null) {
			return 0;
		}
		return 1+Math.max(maxDepth(root.left), maxDepth(root.right));
	}
	
	public static int minDepth(Node root){
		if(root==null){
			return 0;
		}
		return 1+Math.min(minDepth(root.left), minDepth(root.right));
	}
	
	public static boolean isBalanced(Node root){
		return maxDepth(root)-minDepth(root)<=1;
	}
	
	public static Node addToTree(int[] a, int start, int end){
		if(start> end) return null;
		int mid=(start+end)/2;
		Node root=new Node(a[mid]);
		root.left=addToTree(a, start, mid-1);
		root.right=addToTree(a, mid+1, end);
		return root;
	}
	
	public static int countSum(Node root, int sum){
		if(root==null) return 0;
		int count=0;
		int count1=0;
		int count2=0;
		if(root.left==null && root.right==null && root.data==sum){
			count++;
			return count;
		}
		if(root.left!=null){
			count1= countSum(root.left, sum-root.data);
		}
		if (root.right!=null){
			count2=countSum(root.right, sum-root.data);
		}
		count=count1+count2;
		return count;
	}
	static ArrayList<Integer> paths=new ArrayList<Integer>();
	
	public static void printSumPathsFromRoot(Node root, int sum){
		if(root==null) return;
		paths.add(root.data);
		if(root.data==sum){
			
			for(int i=0;i<paths.size();i++)
				System.out.print(paths.get(i)+" ");
	
			System.out.println();
		}
	
		
		printSumPathsFromRoot(root.left,sum-root.data);
		printSumPathsFromRoot(root.right,sum-root.data);
		paths.remove(paths.size()-1);
		
		
	}
	public static void printSumPaths(Node root, int sum){
		
		if(root==null) return;
		
		printSumPathsFromRoot(root,sum);

		printSumPaths(root.left, sum);
		printSumPaths(root.right, sum);
		
	
	
	}
	
	public static boolean covers(Node root, Node a){
		if(root==null) return false;
		if(root==a) return true;
		return (covers(root.left, a) || covers(root.right, a));
	}
	
	public static Node commonAncestor(Node root, Node a, Node b){
		if(root==null) return null;
		if(covers(root.left, a) && covers(root.left, b)){
			return commonAncestor(root.left, a, b);
		}
		if (covers(root.right, a) && covers(root.right, b)){
			return commonAncestor(root.right, a, b);
		}
		return root;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Node root=null;
		//root=Node.insert(root, 9);
		//root=Node.insert(root, 10);
		//root=Node.insert(root, 11);
		//root=insert(root, 12);
		//root=insert(root, 13);
		//root=insert(root, 14);
		//root=insert(root, 25);
		//root=insert(root, 16);
		//root=insert(root, 17);
		//root=insert(root, 18);
		//root=insert(root, 19);
		//inorder(root);
		//System.out.println();
		//preorder(root);
		//System.out.println();
		//postorder(root);
		//System.out.println();
		//System.out.println(isBalanced(root));
		//int[] a={1, 2, 3, 4, 5};
		//root=addToTree(a, 0, a.length-1);
		root=Node.insert(root, 5);
		root=Node.insert(root, 5);
		root=Node.insert(root, 5);
		//root=Node.insert(root, 5);
		root=Node.insert(root, -5);
		//inorder(root);
		printSumPaths(root, 10);
		//System.out.println(countSum(root, 20));

	}

}
