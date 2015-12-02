package knapsack;

import java.util.ArrayList;

public class Main {

static class Node{
int pri;
int mem;
int indSub;
ArrayList<Node> children=new ArrayList<Node>();
public Node(int pri, int mem){
this.pri=pri;
this.mem=mem;
 
}
public void setPri(int pri){
this.pri=pri;
}
public int getPri(){
return pri;
}
public void setMem(int mem){
this.mem=mem;
}
public int getMem(){
return mem;
}
public void setIndSub(int indSub){
this.indSub=indSub;
}
public int getIndSub(){
return indSub;
}
public void addChild(Node n){
children.add(n);
}
 
}
static ArrayList<Node> ordNodes=new ArrayList<Node>();
 
public static int postorderTr(Node root){
 
int sum=0;
if(root.children.size()!=0){
for(Node n : root.children){
 
sum+=postorderTr(n);
 
}
 
}

root.setIndSub(sum+root.children.size());
ordNodes.add(root);
return root.getIndSub();
 
 
 
}
public static void knapSackModified(int nNodes, int availMemory){
 
//this arrays automatically are initialized to 0s, so no need to separately initialize first row and first column
int L[][]=new int[nNodes+1][availMemory+1];
int track[][]=new int[nNodes+1][availMemory+1];
 
 
//construct value and tracking matrices same knapsack approach
for(int i=1;i<nNodes+1;i++){
for(int j=1;j<availMemory+1;j++){
 
if(ordNodes.get(i-1).mem>j)
{
L[i][j]=L[i-1-ordNodes.get(i-1).getIndSub()][j];
track[i][j]=1;
}
 
else{
if(L[i-1-ordNodes.get(i-1).getIndSub()][j]>ordNodes.get(i-1).getPri()+L[i-1][j-ordNodes.get(i-1).getMem()]){
L[i][j]=L[i-1-ordNodes.get(i-1).getIndSub()][j];
track[i][j]=1;
}
else{
L[i][j]=ordNodes.get(i-1).getPri()+L[i-1][j-ordNodes.get(i-1).getMem()];
track[i][j]=2;
}
}
}
 
}
/*for(int i=0;i<nNodes+1;i++){
for(int j=0;j<availMemory+1;j++){
System.out.print(L[i][j]+" ");
}
System.out.println();
}
for(int i=0;i<nNodes+1;i++){
for(int j=0;j<availMemory+1;j++){
System.out.print(track[i][j]+" ");
}
System.out.println();
}*/
 
//print solution
int row=nNodes;
int col=availMemory;
System.out.println("Total priority "+L[row][col]);
System.out.println("Processes to take");
while(row>=1 && col>=1){
//System.out.println(row+"rc"+col);
if(track[row][col]==1){
row=row-1-ordNodes.get(row-1).getIndSub();
}
else{
System.out.println(ordNodes.get(row-1).getPri()+" "+ordNodes.get(row-1).getMem());
 
col=col-ordNodes.get(row-1).getMem();
row--;
}
}
 
}
public static void main(String[] args) {
// TODO Auto-generated method stub
 
//construct your graph (tree)
Node n1=new Node(9,1);
Node n2=new Node(1,12);
Node n3=new Node(10,2);
Node n4=new Node(2,20);
Node n5=new Node(3,7);

n1.addChild(n2);
n1.addChild(n3);
n2.addChild(n4);
n2.addChild(n5);

 
//order nodes in a way that children come before parent (postorder traversal)
postorderTr(n1);
/*for(Node n : ordNodes){
System.out.println(n.getPri()+" "+n.getMem()+" "+n.getIndSub());
}*/
 
int availMemory=14;
int nNodes=5;
 
//run modified knapsack
knapSackModified(nNodes,availMemory);
 
}

}
