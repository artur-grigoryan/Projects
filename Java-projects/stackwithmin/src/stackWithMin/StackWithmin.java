package stackWithMin;

class Node{
	Node next;
	int data;
	int min;
	public Node (int d, int m){
		data=d;
		min=m;
	}
}
public class StackWithmin {
	Node top;
	Node pop(){
		if(top!=null){
			int data=top.data;
			int min=top.min;
			top=top.next;
			return new Node (data, min);
		}
		return null;
	}
	
	void push(int data){
		if(top!=null){
			Node tmp=new Node (data, data);
			tmp.next=top;
			tmp.min=Math.min(tmp.min, top.min);
			top=tmp;
		}
		else {
			top = new Node(data, data);
			
		}
		
	}
	
	public int min(){
		return top.min;
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		StackWithmin s = new StackWithmin();
		s.push(2);
		s.push(3);
		s.push(4);
		s.push(1);
		s.push(5);
		s.push(6);
		System.out.println(s.min());
		s.pop();
		System.out.println(s.min());
		s.pop();
		System.out.println(s.min());
		s.pop();
		System.out.println(s.min());
		s.pop();
		System.out.println(s.min());
		s.pop();
		System.out.println(s.min());
		s.pop();
		System.out.println(s.min());
		s.pop();
		System.out.println(s.min());
		s.pop();
		System.out.println(s.min());
	}

}
