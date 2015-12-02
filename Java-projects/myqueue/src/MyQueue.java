import java.util.Stack;


public class MyQueue {
	Stack<Integer> s1, s2;
	public MyQueue(){
		s1=new Stack<Integer>();
		s2=new Stack<Integer>();
	}
	
	public int size(){
		return s1.size()+s2.size();
	}
	
	public void add(Integer data){
		s1.push(data);
	}
	
	public Integer remove(){
		if(!s2.empty()) {
			return s2.pop();
		}
		while(!s1.empty())
			s2.push(s1.pop());
		return s2.pop();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyQueue queue = new MyQueue();
		queue.add(5);
		queue.add(6);
		queue.add(7);
		//System.out.println(queue.remove());

	}

}
