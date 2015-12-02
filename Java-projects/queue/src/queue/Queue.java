package queue;

class Node{
	int data;
	Node next;
	public Node(int d){
		data=d;
		next=null;
	}
}

public class Queue {
	Node first;
	Node last;
	public void enque(int d){
		if(first==null){
			first = new Node(d);
			last = first;
		}
		else {
			Node n=new Node (d);
			n.next=first;
			first=n;
		}
	}
	
	void deque(){
		if(last!=null){
			System.out.println(last.data);
			Node n= first;
			while(n.next.next!=null){
				n=n.next;
			}
			n.next=null;
			last=n;
		}
	}
	
	void dequeMax(){
		if(first!=null){
			Node n=first;
			Node max=first;
			Node max_prev=null;
			while(n.next!=null){
				if(n.next.data>max.data){
					max=n.next;
					max_prev=n;
				}
				n=n.next;
			}
			if(max_prev==null){
				System.out.println(first.data);
				first.data=first.next.data;
				first.next=first.next.next;
			}
			else{
				System.out.println(max.data);
				max_prev.next=max.next;
				
		
			}
		}
	}
	void print(Node n){
		if(n==null) return;
		else{
			Node temp=n;
			while(temp!=null){
				System.out.print(temp.data+" ");
				temp=temp.next;
			}
			System.out.println();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Queue q=new Queue();
		q.enque(5);
		q.enque(6);
		q.enque(7);
		q.print(q.first);
		q.dequeMax();
		q.print(q.first);

	}

}
