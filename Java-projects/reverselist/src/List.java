class Node {
	Node next;
	int data;
	public Node(int d){
		data=d;
	}
}
public class List {
	public Node head;
	public void insert(int k){
		Node n= new Node (k);
		n.next=head;
		head=n;
	}
	public void printList(){
		Node n=head;
		while(n!=null){
			System.out.print(n.data+" ");
			n=n.next;
		}
		System.out.println();
	}
	public void reverse(){
		Node previous=null;
		Node current=head;
		if(current==null || current.next==null) return;
		while(current!=null){
			Node tmp=current.next;
			current.next=previous;
			previous=current;
			current=tmp;
		}
		head=previous;
	}
	
	public static Node meetPoint(Node head1, Node head2){
		if(head1==null || head2==null) return null;
		int length1=0;
		int length2=0;
		Node n1=head1;
		Node n2=head2;
		while(n1!=null){
			length1++;
			n1=n1.next;
		}
		while(n2!=null){
			length2++;
			n2=n2.next;
		}
		int dif=length1-length2;
		Node tmp1=head1;
		Node tmp2=head2;
		if(dif>0){
			
			for (int i=0; i<dif; i++){
				tmp1=tmp1.next;
			}
			
			while(tmp1!=tmp2 && tmp1!=null){
				tmp1=tmp1.next;
				tmp2=tmp2.next;
			}
			return tmp1;
		}
		if(dif<0){
			
			for(int i=0; i<-dif; i++){
				tmp2=tmp2.next;
			}
			
			while (tmp1!=tmp2 && tmp1!=null){
				tmp1=tmp1.next;
				tmp2=tmp2.next;
			}
			return tmp1;
		}
		while(tmp1!=tmp2 && tmp1!=null){
			tmp1=tmp1.next;
			tmp2=tmp2.next;
		}
		return tmp1;
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List l=new List();
		l.insert(4);
		l.insert(5);
		l.insert(6);
		l.printList();
		//l.reverse();
		List m=new List();
		m.insert(4);
		m.insert(5);
		m.insert(8);
		m.insert(4);
		m.printList();
		Node meet=meetPoint(l.head, m.head);
		System.out.println(meet.data);
	}
}

