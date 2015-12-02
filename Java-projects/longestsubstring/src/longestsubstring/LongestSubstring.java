package longestsubstring;

public class LongestSubstring {
	public static String longestWithRec(String s1, String s2){
		String a;
		String b;
		if(s1==null || s2==null) return null;
		if(s1.length()==0 || s2.length()==0) return"";
		
		else if(s1.charAt(s1.length()-1)==s2.charAt(s2.length()-1)){
			return longestWithRec(s1.substring(0, s1.length()-1), s2.substring(0, s2.length()-1))+s1.charAt(s1.length()-1);
		}
		else {
			a=longestWithRec(s1, s2.substring(0, s2.length()-1));
			b=longestWithRec(s1.substring(0, s1.length()-1), s2);
		}
		return (a.length()>b.length())?a:b;
		
	}
	
	public static String longestWithIter(String s1, String s2){
		StringBuilder str=new StringBuilder();
		if(s1==null || s2==null || s1.length()==0 || s2.length()==0){
			return "";
		}
		int[][] num = new int[s1.length()][s2.length()];
		int maxlen=0;
		int lastSubBegin=0;
		for (int i=0; i<s1.length(); i++){
			for (int j=0; j<s2.length(); j++){
				if(s1.charAt(i)==s2.charAt(j)){
					if (i==0 || j==0){
						num[i][j]=1;
					}
					else num[i][j] = 1+num[i-1][j-1];
					if(num[i][j]>maxlen){
						maxlen=num[i][j];
						int thisSubBegin=i-num[i][j]+1;
						if(lastSubBegin==thisSubBegin){
							str.append(s1.charAt(i));
						}
						else{
							lastSubBegin=thisSubBegin;
							str=new StringBuilder();
							str.append(s1.substring(lastSubBegin, i+1));
						}
					}
				}
			}
		}
		return str.toString();
	}
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String a="absdeaaa";
		String b="sbsd";
		System.out.println(longestWithIter(a, b));

	}

}
