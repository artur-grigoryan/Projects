class Node {
		Node next;
		int data;
		public Node(int d) {data=d; next = null;}
}
public class List {
	Node head;
	public void insert(int d){
		Node n=new Node(d);
		n.next=head;
		head=n;
	}
	
	public Node remove(){
		if(head==null) return null;
		Node tmp=head;
		head=head.next;
		return tmp;
	}
	public void printlist(){
		Node n=head;
		while(n!=null){
			System.out.print(n.data + " ");
			n=n.next;
		}
		System.out.println();
	}
	public void order(){
		if(head==null) return;
		Node n=head;
		Node start=null;
		Node start1=null;
		Node start2=null;
		Node current0=null;
		Node current1=null;
		Node current2=null;
		while(n!=null){
			if(n.data==0){
				if(current0==null){
					current0=n;
					start=n;
				}
				current0.next=n;
				current0=current0.next;
			}
			if(n.data==1){
				if(current1==null){
					current1=n;
					start1=n;
				}
				current1.next=n;
				current1=current1.next;
			}
			if(n.data==2){
				if(current2==null){
					current2=n;
					start2=n;
				}
				current2.next=n;
				current2=current2.next;
			}
			n=n.next;			
		}
		if (start!=null){
			if(start1!=null){
				current0.next=start1;
				if(start2!=null) {
					current1.next=start2;
				}
			}
		}
		
		if(start1!=null){
			start=start1;
			if(start2!=null){
				current1.next=start2;
			}
		}
		start=start2;
		
		while(start!=null){
			System.out.print(start.data +" ");
			start=start.next;
		}
		System.out.println();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List a = new List();
		a.insert(1);
		a.insert(0);
		a.insert(2);
		a.insert(1);
		a.printlist();
		a.order();
		

	}

}
