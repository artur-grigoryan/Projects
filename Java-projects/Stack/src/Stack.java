class Node {
	Node next;
	int data;
	public Node (int d){
		data=d;
		next=null;
	}
	
}


public class Stack {
	Node top;
	Node pop(){
		if (top!=null){
			
			int item=top.data;
			top=top.next;
			return new Node(item);
		}
		return null;
	}
	
	void push (int item){
		Node tmp = new Node (item);
		tmp.next=top;
		top=tmp;
	}
	
	public int peek(){
		return top.data;
	}
	
	public boolean isEmpty(){
		return top==null;
	}
	
	public static Stack sortStack(Stack s){
		if(s==null) return null;
		Stack res = new Stack();
		while(!s.isEmpty()){
			int tmp=s.pop().data;
			while(!res.isEmpty() && res.peek() > tmp){
				s.push(res.pop().data);
			}
			res.push(tmp);
		}
		return res;
	}
	public static void print(Stack s){
		if (s==null) return;
		while(!s.isEmpty()){
			System.out.print(s.pop().data + " ");			
		}
		System.out.println();
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Stack s = new Stack();
		s.push(6);
		s.push(5);
		s.push(3);
		s.push(4);
		s.push(2);
		print(sortStack(s));
		

	}

}
