import java.util.Hashtable;

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
	public int sum(){
		if(head==null) return -1;
		int sum=0;
		Node n=head;
		while(n!=null){
			sum=sum+n.data;
			n=n.next;
		}
		return sum;
		
	}
	public void removeDups(){
		if(head==null) return;
		Node previous = head;
		Node current=previous.next;
		while(current!=null){
			Node runner=head;
			while(runner!=current){
				if(runner.data==current.data){
					Node tmp=current.next;
					previous.next=tmp;
					current=tmp;
					break;
				}
				runner=runner.next;
			}
			if(runner==current){
				previous=current;
				current=current.next;
			}
		}
	}
		
		public void removeDups2(){
			if(head==null) return;
			Hashtable<Integer, Boolean> table = new Hashtable<Integer, Boolean>();
			Node current=head;
			Node previous=null;
			while (current!=null){
				if(table.containsKey(current.data)){
					previous.next=current.next;
				}
				else {
					table.put(current.data, true);
					previous=current;
				}
				current=current.next;
			}
			
		}
		
		public int findLast(){
			if(head==null) return -1;
			Node n=head;
			while(n.next!=null){
				n=n.next;
			}
			
			return n.data;
		}
		
		public int findLastN (int n){
			if(head==null || n<=0) return -1;
			Node k=head;
			Node runner=head;
			for (int i=0; i<n; i++){
				if(runner!=null){
					runner=runner.next;
				}
				else return -1;
			}
			while(runner!=null){
				k=k.next;
				runner=runner.next;
			}
			return k.data;
		}
		
		public void deleteFirstMatch(int d){
			if(head==null) return;
			Node n=head;
			while(n.next.next!=null){
				n=n.next;
				if(n.data==d) break;
			}
			if(n.next.next!=null){
				n.data=n.next.data;
				n.next=n.next.next;
			}
			else if(n.next.data==d && n.next.next==null){
				n.next=null;
			}
			
		}
		public static void add(Node n1, Node n2){
			if(n1==null || n2==null) return;
			//if(n1==null);
			//if(n2==null);
			Node p1=n1;
			Node p2=n2;
			while(p2!=null){
				int sum=(p1.data+p2.data)%10;
				int tmp=(p1.data+p2.data)/10;
				p1.data=sum;
				if(tmp==1){
					if(p1.next!=null){
						p1.next.data++;
					}
					else{
						Node next=new Node(tmp);
						p1.next=next;
					}
				}
				p1=p1.next;
				p2=p2.next;
			}
		}
		
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List a = new List();
		List b = new List();
		a.insert(9);
		a.insert(9);
		a.insert(9);
		b.insert(3);
		b.insert(9);
		b.insert(9);
		b.insert(9);
		a.insert(9);
		a.printlist();
		b.printlist();
		//a.deleteFirstMatch(4);
		//a.removeDups2();
		Node n1=a.head;
		Node n2=b.head;
		add(n1, n2);
		a.printlist();
		//System.out.println(sum.data);
		//System.out.println(a.findLastN(2));
		
		
		
		

	}

}
