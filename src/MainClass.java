import java.sql.*;
import java.text.*;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class MainClass {

	public static boolean isValidDate(String data) {
	    try {
	    	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	        format.parse(data);
	        return true;
	    } catch (ParseException e) {
	        return false;
	    }
	}
	public static void main(String[] args) throws SQLException,ClassNotFoundException,ParseException{
		// TODO Auto-generated method stub
		SimpleDateFormat sdformat = new SimpleDateFormat("dd/MM/yyyy");
		JFrame jf1=new JFrame("Details");
		JPanel jp1=new JPanel();
		Class.forName("com.mysql.jdbc.Driver");
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/b?" + "autoReconnect=true&useSSL=false","root","muskanpebe12");
		JLabel jl1,jl2,jl3,jl4,jl5;
		JTextField jt1,jt2,jt3,jt4,jt5;
		jl1=new JLabel("Name of the product");
		jl2=new JLabel("Type");
		jl3=new JLabel("Date of Purchase");
		jl4=new JLabel("Price");
		jl5=new JLabel("Date of Expiry");
		jt1=new JTextField(20);
		jt2=new JTextField(10);
		jt3=new JTextField(10);
		jt4=new JTextField(10);
		jt5=new JTextField(10);
		jt1.setText("");
		jt2.setText("");
		jt3.setText("");
		jt4.setText("");
		jt5.setText("");
		JTextArea displayTextArea = new JTextArea();
        JScrollPane jScrollPane = new JScrollPane(displayTextArea);
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		JButton jb1,jb2,jb3,jb4;
		jb1=new JButton("Submit");
		jb1.addActionListener(new ActionListener() {
            //@Override
            public void actionPerformed(ActionEvent e) {
                String name = jt1.getText();
                String type = jt2.getText();
                String dop = jt3.getText();
                String price =jt4.getText();
                String doe= jt5.getText();
                String query="insert into medicine1 values(?,?,?,?,?)";
                if(name.equals("") || type.equals("") || dop.equals("") || price.equals("") || doe.equals(""))
                {
                	JOptionPane.showMessageDialog(null,"Some Fields are empty!");
                }
                else if(!isValidDate(doe) || !isValidDate(dop))
                {
                	JOptionPane.showMessageDialog(null,"Date format is incorrect!");
                }
                else
                {
                	try {
    					PreparedStatement ps=con.prepareStatement(query);
    					ps.setString(1,name);
    					ps.setString(2,type);
    					ps.setString(3,dop);
    					ps.setString(4,doe);
    					ps.setString(5,price);
    					int i=ps.executeUpdate();
    					jt1.setText("");
    					jt2.setText("");
    					jt3.setText("");
    					jt4.setText("");
    					jt5.setText("");
    					JOptionPane.showMessageDialog(null,"Details are succesfully updated!");
    				} catch (SQLException e1) {
    					// TODO Auto-generated catch block
    					e1.printStackTrace();
    				}
                }
                }
                
        });
		jb2=new JButton("Update from dop");
		jb2.addActionListener(new ActionListener() {
            //@Override
            public void actionPerformed(ActionEvent e) {
				
                a: try {
                	String a="";
                	a=JOptionPane.showInputDialog("Enter date of purchase");
                	if(a.equals(""))
             	   {
             		   JOptionPane.showMessageDialog(null, "Field is empty!");
 						JOptionPane.showMessageDialog(null,"Updation is not successful!");
 						break a;
             	   }
                	else if(!isValidDate(a))
					{
						JOptionPane.showMessageDialog(null, "Date is not in valid form!");
						JOptionPane.showMessageDialog(null,"Updation is not successful!");
						break a;
					}
                	String[] ch= {"type","name","doe","price"};
                	int i = JOptionPane.showOptionDialog(null, "Enter attribute to update", "Attribute", 0,
                        JOptionPane.INFORMATION_MESSAGE, null, ch, ch[0]);
                	String l=ch[i];
                	String in="";
                	in=JOptionPane.showInputDialog(null,"Enter "+ch[i]+" to update");
                	String q1="";

            	   if(in.equals(""))
            	   {
            		   JOptionPane.showMessageDialog(null, "Field is empty!");
						JOptionPane.showMessageDialog(null,"Updation is not successful!");
						break a;
            	   }
                	if(i==0)
    				{
    					q1="update medicine1 set type=? where dop=?";
    				}
    				else if(i==1)
    				{
    					q1="update medicine1 set name=? where dop=?";
    				}
    				else if(i==2)
    				{
    					if(!isValidDate(in))
    					{
    						JOptionPane.showMessageDialog(null, "Date is not in valid form!");
    						JOptionPane.showMessageDialog(null,"Updation is not successful!");
    						break a;
    					}
    					q1="update medicine1 set doe=? where dop=?";
    				}
    				else if(i==3)
    				{
    					q1="update medicine1 set price=? where dop=?";
    				}
					PreparedStatement ps1=con.prepareStatement(q1);
					ps1.setString(1,in);
					ps1.setString(2,a);
					int k=ps1.executeUpdate();
					if(k==1)
					{
						JOptionPane.showMessageDialog(null,"Updated Successfully!");
					}

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });
		a:
		jb3=new JButton("Display");
		jb3.addActionListener(new ActionListener() {
            //@Override
            public void actionPerformed(ActionEvent e) {
                String s1 = "Name\tType\tDoP\tDoE\tPrice\n";
                String q2="select * from medicine1";
        		PreparedStatement ps2;
				try {
					ps2 = con.prepareStatement(q2);
					ResultSet rs=ps2.executeQuery();
	        		String a="name";
	        		String b="type";
	        		String c="dop";
	        		String d="doe";
	        		String p="price";
	        		while(rs.next())
	        		{
	        			s1=s1+rs.getString(a)+"\t"+rs.getString(b)+"\t"+rs.getString(c)+"\t"+rs.getString(d)+"\t"+rs.getString(p)+"\n";
	        			
	        		}
	        		displayTextArea.setText(s1);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        		
            }
        });
		jb4=new JButton("Delete from expiry date");
		jb4.addActionListener(new ActionListener() {
            //@Override
            public void actionPerformed(ActionEvent e) {
        		PreparedStatement ps3,ps4;
				try {
					String q3="select * from medicine1";
					String q4="delete from medicine1 where doe=?";
					ps3 = con.prepareStatement(q3);
					ResultSet rs1=ps3.executeQuery();
	        		String doe1="doe";
	        		while(rs1.next())
	        		{
	        			String g=rs1.getString(doe1);
	        		      Date d1 = sdformat.parse("10/04/2020");
	        		      Date d2 = sdformat.parse(g);
	        			if(d1.compareTo(d2)>0)
	        			{
	        				ps4=con.prepareStatement(q4);
	        				ps4.setString(1, g);
	        				int j=ps4.executeUpdate();
	        			}
	        			
	        			
	        		}
	        		JOptionPane.showMessageDialog(null, "Expired Items deleted successfully!");
				} catch (SQLException | ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        		
            }
        });
		jp1.add(jl1);
		jp1.add(jt1);
		jp1.add(jl2);
		jp1.add(jt2);
		jp1.add(jl3);
		jp1.add(jt3);
		jp1.add(jl4);
		jp1.add(jt4);
		jp1.add(jl5);
		jp1.add(jt5);
		jp1.add(jb1);
		jp1.add(jb2);
		jp1.add(jb4);
		jf1.add(jp1);
		JPanel jp2 = new JPanel();
        jp2.add(jb3);
        jp2.add(jScrollPane);
        jf1.add(jp2);
        jf1.setVisible(true);
        jf1.setSize(600, 400);
        jf1.setLayout(new GridLayout(2,1));
        jp1.setLayout(new GridLayout(7,2));
        jp2.setLayout(new GridLayout(2,1));
        jf1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		
		
		
		
		
		
	}

}
