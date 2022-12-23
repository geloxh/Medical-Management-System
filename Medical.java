import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.event.*;
import javax.swing.table.*;
import java.sql.*;
import java.awt.event.TextEvent;
class MenuPage extends JFrame implements ActionListener
{
int total=0,qty;
String distot="Total Price: ";
JLabel totp;
JLabel l1,l2,l3,l4,l5,edpro1,edpro2,edpro3,edpro4,edpro5,edpro6,edpro7,edpro8,loglabel,usernm,pass,cpass;
CardLayout c1,c2;
Container c;
JButton b1,b2,b3,b4,b5,logout,close;
JTextField t1,t2,t3,t4,t5,t6,t7,t8,t9,usertxt;
JButton addsupp, delsupp, addprod,delprod,updtprod,deluser,cgpass;
JButton saveSupp1;
JPasswordField passtxt,cpasstxt,newpasstxt;
// ******************************************
DefaultTableModel dtm1,dtm2,dtm3,dtm4;
Object[] obj1=new Object[8];//products
Object[] obj2=new Object[5];//suppliers
Object[] obj3=new Object[5];//sales
Object[] obj4=new Object[5];
//*************************************
JPanel mpanel, medicPanel,salesPanel,addproPanel,supplierPanel,SettingPanel, purchasePanel;
JTable medicTable,supplierTable,recordTable,ptable;
JScrollPane sp1,sp2,sp3,sp4;
///////////////////////////////
JPanel AddSuppPanel;
JLabel sn,seid,smn,splc,spro;
JTextField st1,st2,st3,st4,st5;
///////////////////////C O M P O N E N T S   F O R   P U R C H A S E /////////////////////
JTextField searchbar,pname,pqty,pprice,pexp;
JLabel sb,pn,pq,pp;
JButton srchbtn,addp;
//////////////////////////////////////////////////////////////////////////////////////////
//public void InsertProducts()
//{

	
//}

MenuPage()
{
///////////////////create table////////////
try{
Class.forName("com.mysql.cj.jdbc.Driver");
Connection con4=DriverManager.getConnection("jdbc:mysql://localhost:3306/nikita","root","root");
String sql="create table products(Product_ID int(4),Product_Name varchar(50),Product_type varchar(40),Expiry_Date date,Company varchar(50),Stored_At varchar(30),Quantity int(5),Price int(5))";
PreparedStatement ps2=con4.prepareStatement(sql);
ps2.executeUpdate();
ps2.close();
con4.close();
} catch(Exception e){}
//////////////////////
try{
Class.forName("com.mysql.cj.jdbc.Driver");
Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/nikita","root","root");
String sql="create table suppliers(name varchar(30),contact_no bigint(10),Email_id varchar(40),Place varchar(30),Supplied_Product varchar(50))";
PreparedStatement ps=con.prepareStatement(sql);
ps.executeUpdate();
ps.close();
con.close();
} catch(Exception e){}
//////////////////////////////////////////	
try{
Class.forName("com.mysql.cj.jdbc.Driver");
Connection con4=DriverManager.getConnection("jdbc:mysql://localhost:3306/nikita","root","root");
String sql="create table sales(Product_Name varchar(30),Expiry_Date varchar(30),Quantity int(4),Price int(5),Purchase_Date varchar(30))";
PreparedStatement ps2=con4.prepareStatement(sql);
ps2.executeUpdate();
ps2.close();
con4.close();
} catch(Exception e){}





Color DarkRed=new Color(204,0,0);
Color drkgreen=new Color(0,120,0);
Color lightblue=new Color(0,120,120);
c=getContentPane();
c.setBackground(Color.white);
setSize(1400,735);
setVisible(true);
setBackground(Color.black);
setLayout(null);
ImageIcon i1=new ImageIcon("medicine.jpg");
b1=new JButton(i1);
b1.setBounds(500,10,70,70);
ImageIcon i2=new ImageIcon("supplier.jpg");
b2=new JButton(i2);
b2.setBounds(600,10,70,70);
ImageIcon i3=new ImageIcon("sales.jpg");
b3=new JButton(i3);
b3.setBounds(700,10,70,70);
ImageIcon i4=new ImageIcon("addproduct.jpg");
b4=new JButton(i4);
b4.setBounds(800,10,70,70);
ImageIcon i5=new ImageIcon("settings.jpg");
b5=new JButton(i5);
b5.setBounds(900,10,70,70);
ImageIcon i7=new ImageIcon("close.jpg");
close=new JButton(i7);
close.setBounds(1200,108,40,39);
close.setVisible(false);
ImageIcon i6=new ImageIcon("logout.jpg");
logout=new JButton(i6);
logout.setBounds(1295,10,60,50);
logout.setBackground(Color.white);
logout.addActionListener(this);
loglabel=new JLabel("Logout");
loglabel.setBounds(1301,60,60,20);
l1=new JLabel("Products");
l1.setBounds(506,80,80,20);
l2=new  JLabel("Suppliers");
l2.setBounds(605,80,80,20);
l3=new JLabel("View Sales Record");
l3.setBounds(680,80,130,20);
l4=new  JLabel("Edit Products");
JLabel l6=new JLabel("Details");
l4.setBounds(805,80,80,20);
l6.setBounds(812,100,80,20);
l5=new JLabel("Settings");
l5.setBounds(908,80,80,20);
b1.setBorderPainted(false);
b2.setBorderPainted(false);
b3.setBorderPainted(false);
b4.setBorderPainted(false);
b5.setBorderPainted(false);
logout.setBorderPainted(false);
close.setBorderPainted(false);
add(loglabel);add(l1);add(l2);add(l3);add(l4);add(l5);add(l6);add(b1);add(b2);add(b3);add(b4);add(b5);add(logout);add(close);
close.addActionListener(this);
b1.addActionListener(this);
b2.addActionListener(this);
b3.addActionListener(this);
b4.addActionListener(this);
b5.addActionListener(this);
logout.addActionListener(this);
c1=new CardLayout();
mpanel=new JPanel(c1);
mpanel.setBounds(120,120,1100,500);
mpanel.setBackground(Color.white);
/////////////////////////////PURCHASE//////////////////////////////////////////
purchasePanel=new JPanel();
purchasePanel.setLayout(null);
purchasePanel.setBounds(120,120,1100,500);
purchasePanel.add(new JLabel(""));
purchasePanel.setBackground(Color.white);
purchasePanel.setBorder(BorderFactory.createTitledBorder("PURCHASE"));
searchbar= new JTextField(80);
searchbar.setBounds(30,20,500,40);
srchbtn=new JButton("SEARCH PRODUCT");
srchbtn.setBounds(550,20,200,40);
srchbtn.setBackground(lightblue);
srchbtn.setFont(new Font("Courier",Font.BOLD,18));
srchbtn.setForeground(Color.white);
pname=new JTextField(20);
pname.setBounds(230,100,190,30);
pexp=new JTextField(20);
pexp.setBounds(230,150,190,30);
pqty=new JTextField(20);
pqty.setBounds(230,200,190,30);
pprice=new JTextField(20);
pprice.setBounds(230,250,190,30);

pn=new JLabel("Product Name");
pn.setBounds(30,100,190,30);
pn.setFont(new Font("Courier",Font.BOLD,18));
JLabel pe=new JLabel("Expiry Date");
pe.setBounds(30,150,190,30);
pe.setFont(new Font("Courier",Font.BOLD,18));

pq=new JLabel("Product Quantity");
pq.setBounds(30,200,190,30);
pq.setFont(new Font("Courier",Font.BOLD,18));

pp=new JLabel("Product Price");
pp.setBounds(30,250,190,30);
pp.setFont(new Font("Courier",Font.BOLD,18));

addp=new JButton("ADD");
addp.setBounds(100,350,200,40);
addp.setBackground(Color.black);
addp.setForeground(Color.white);
addp.setFont(new Font("Courier",Font.BOLD,18));

String col4[]={"Product Name","Expiry Date","Quantity","Price","Purchase_Date"};
dtm4=new DefaultTableModel(col4,0);
ptable=new JTable(dtm4);
ptable.createDefaultColumnsFromModel();
ptable.setColumnSelectionAllowed(true);
ptable.setRowSelectionAllowed(true);
sp4=new JScrollPane(ptable);
sp4.setBounds(500,100,550,300);
purchasePanel.add(sp4);

totp=new JLabel("");
totp.setBounds(500,400,400,30);
totp.setFont(new Font("Courier",Font.BOLD,25));
purchasePanel.add(searchbar);
purchasePanel.add(srchbtn);
purchasePanel.add(pn);
purchasePanel.add(totp);
repaint();
purchasePanel.add(pname);
purchasePanel.add(pe);purchasePanel.add(pexp);
purchasePanel.add(pq);purchasePanel.add(pqty);
purchasePanel.add(pp);purchasePanel.add(pprice);
purchasePanel.add(addp);
srchbtn.addActionListener(this);
addp.addActionListener(this);
add(purchasePanel);

// **********************************TABLE 1 ******************************************
medicPanel=new JPanel();
medicPanel.setLayout(null);
medicPanel.setBorder(BorderFactory.createTitledBorder("PRODUCTS"));
medicPanel.setBackground(Color.white);
String col1[]={"Product ID","Product Name","Product type","Expiry Date","Company","Stored At","Quantity","Price"};
dtm1=new DefaultTableModel(col1,0);
medicTable=new JTable(dtm1);
medicTable.createDefaultColumnsFromModel();
medicTable.setColumnSelectionAllowed(true);
medicTable.setRowSelectionAllowed(true);

delprod=new JButton("DELETE PRODUCT");
delprod.setBackground(DarkRed);
delprod.setFont(new Font("Courier",Font.BOLD,18));
delprod.setBounds(830,440,200,40);
medicPanel.add(delprod);
sp1=new JScrollPane(medicTable);
sp1.setBounds(5,30,1090,400);
medicPanel.add(sp1);
delprod.addActionListener(this);
//************************************************************************************************
// ****************************** ****TABLE 3 ******************************************

salesPanel=new JPanel();
salesPanel.setLayout(null);
salesPanel.setBorder(BorderFactory.createTitledBorder("SALES RECORD"));
salesPanel.setBackground(Color.white);
String col3[]={"Product Name","Expiry Date","Quantity","Price","Purchase_Date"};
dtm3=new DefaultTableModel(col3,0);
recordTable=new JTable(dtm3);
recordTable.createDefaultColumnsFromModel();
recordTable.setColumnSelectionAllowed(true);
recordTable.setRowSelectionAllowed(true);
sp3=new JScrollPane(recordTable);
sp3.setBounds(5,30,1090,465);
salesPanel.add(sp3);

// **********************************add/update/delete ******************************************
addproPanel=new JPanel();
addproPanel.setBorder(BorderFactory.createTitledBorder("EDIT PRODUCT DETAILS"));
addproPanel.setLayout(null);
addproPanel.setBackground(Color.white);
//-----------------------------------------------------------------------------------------------------------
edpro1=new JLabel("Product ID");
edpro1.setFont(new Font("Courier",Font.BOLD,20));
edpro1.setBounds(300,30,300,30);
edpro2=new JLabel("Product Name");
edpro2.setFont(new Font("Courier",Font.BOLD,20));
edpro2.setBounds(300,70,300,30);
edpro3=new JLabel("Product Type");
edpro3.setFont(new Font("Courier",Font.BOLD,20));
edpro3.setBounds(300,110,300,30);
edpro4=new JLabel("Expiry Date");
edpro4.setFont(new Font("Courier",Font.BOLD,20));
edpro4.setBounds(300,150,300,30);
edpro5=new JLabel("Company");
edpro5.setFont(new Font("Courier",Font.BOLD,20));
edpro5.setBounds(300,190,300,30);
edpro6=new JLabel("Stored At");
edpro6.setFont(new Font("Courier",Font.BOLD,20));
edpro6.setBounds(300,230,300,30);
edpro7=new JLabel("Quantity ");
edpro7.setFont(new Font("Courier",Font.BOLD,20));
edpro7.setBounds(300,270,300,30);
edpro8=new JLabel("Price");
edpro8.setFont(new Font("Courier",Font.BOLD,20));
edpro8.setBounds(300,310,300,30);

t1=new JTextField(20);
t1.setBounds(550,30,250,30);
t2=new JTextField(25);
t2.setBounds(550,70,250,30);
t3=new JTextField(25);
t3.setBounds(550,110,250,30);
t4=new JTextField(25);
t4.setBounds(550,150,250,30);
t5=new JTextField(25);
t5.setBounds(550,190,250,30);
t6=new JTextField(25);
t6.setBounds(550,230,250,30);
t7=new JTextField(25);
t7.setBounds(550,270,250,30);
t8=new JTextField(25);
t8.setBounds(550,310,250,30);
t1.setFont(new Font("Courier",Font.PLAIN,18));
t2.setFont(new Font("Courier",Font.PLAIN,18));
t3.setFont(new Font("Courier",Font.PLAIN,18));
t4.setFont(new Font("Courier",Font.PLAIN,18));
t5.setFont(new Font("Courier",Font.PLAIN,18));
t6.setFont(new Font("Courier",Font.PLAIN,18));
t7.setFont(new Font("Courier",Font.PLAIN,18));
t8.setFont(new Font("Courier",Font.PLAIN,18));
addprod=new JButton("ADD PRODUCT");
addprod.setFont(new Font("Courier",Font.BOLD,18));
addprod.setBounds(430,360,200,40);
addprod.setBackground(lightblue);
addprod.setForeground(Color.white);
addproPanel.add(edpro1);
addproPanel.add(t1);
addproPanel.add(edpro2);
addproPanel.add(t2);
addproPanel.add(edpro3);
addproPanel.add(t3);
addproPanel.add(edpro4);
addproPanel.add(t4);
addproPanel.add(edpro5);
addproPanel.add(t5);
addproPanel.add(edpro6);
addproPanel.add(t6);
addproPanel.add(edpro7);
addproPanel.add(t7);
addproPanel.add(edpro8);
addproPanel.add(t8);
addproPanel.add(addprod);

addprod.addActionListener(this);
//----------------------------------------------------------------------------
// **********************************TABLE 2******************************************
supplierPanel=new JPanel();
supplierPanel.setBorder(BorderFactory.createTitledBorder("SUPPLIERS"));
supplierPanel.setLayout(null);
supplierPanel.setBackground(Color.white);
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
String col2[]={"Supplier Name","Contact no.","Email ID","Place","Product Supplied"};
dtm2=new DefaultTableModel(col2,0);
supplierTable=new JTable(dtm2);
supplierTable.createDefaultColumnsFromModel();
supplierTable.setColumnSelectionAllowed(true);
supplierTable.setRowSelectionAllowed(true);
sp2=new JScrollPane(supplierTable);
sp2.setBounds(5,30,1090,400);
addsupp=new JButton("Add Supplier");
addsupp.setFont(new Font("Verdana",Font.PLAIN,19));
addsupp.setBackground(drkgreen);
//addsupp.setForeground(Color.);
addsupp.setBounds(900,440,0,0);
addsupp.setSize(180,40);
delsupp=new JButton("Delete Supplier");
delsupp.setFont(new Font("Verdana",Font.PLAIN,19));
delsupp.setBounds(670,440,0,0);
delsupp.setBackground(DarkRed);
delsupp.setSize(200,40);
supplierPanel.add(sp2);
supplierPanel.add(addsupp);
supplierPanel.add(delsupp);
addsupp.addActionListener(this);
delsupp.addActionListener(this);
/////////////////////////////////////////////////////////////////////////////////////////
AddSuppPanel=new JPanel();
AddSuppPanel.setBounds(120,120,1100,500);
AddSuppPanel.setBackground(Color.white);
AddSuppPanel.setLayout(null);
AddSuppPanel.setBorder(BorderFactory.createTitledBorder("ADD SUPPLIER"));
saveSupp1=new JButton("SAVE");
saveSupp1.setBackground(lightblue);
saveSupp1.setForeground(Color.white);
saveSupp1.setBounds(400,360,200,40);
sn=new JLabel("Supplier Name");
sn.setFont(new Font("Courier",Font.BOLD,20));
sn.setBounds(300,110,300,30);
smn=new JLabel("Contact no.");
smn.setFont(new Font("Courier",Font.BOLD,20));
smn.setBounds(300,150,300,30);
seid=new JLabel("Email ID");
seid.setFont(new Font("Courier",Font.BOLD,20));
seid.setBounds(300,190,300,30);
splc=new JLabel("Place");
splc.setFont(new Font("Courier",Font.BOLD,20));
splc.setBounds(300,230,300,30);
spro=new JLabel("Product Supplied");
spro.setFont(new Font("Courier",Font.BOLD,20));
spro.setBounds(300,270,300,30);
st1=new JTextField(20);
st1.setBounds(550,110,250,30);
st2=new JTextField(25);
st2.setBounds(550,150,250,30);
st3=new JTextField(25);
st3.setBounds(550,190,250,30);
st4=new JTextField(25);
st4.setBounds(550,230,250,30);
st5=new JTextField(25);
st5.setBounds(550,270,250,30);
st1.setFont(new Font("Courier",Font.PLAIN,18));
st2.setFont(new Font("Courier",Font.PLAIN,18));
st3.setFont(new Font("Courier",Font.PLAIN,18));
st4.setFont(new Font("Courier",Font.PLAIN,18));
st5.setFont(new Font("Courier",Font.PLAIN,18));






AddSuppPanel.add(sn);AddSuppPanel.add(smn);AddSuppPanel.add(seid);AddSuppPanel.add(spro);AddSuppPanel.add(splc);//labels
AddSuppPanel.add(st1);AddSuppPanel.add(st2);AddSuppPanel.add(st3);AddSuppPanel.add(st4);AddSuppPanel.add(st5);;

AddSuppPanel.add(saveSupp1);
add(AddSuppPanel);
AddSuppPanel.setVisible(false);
saveSupp1.addActionListener(this);





// **********************************Settings ****************************************
SettingPanel=new JPanel();
SettingPanel.setBorder(BorderFactory.createTitledBorder("SETTINGS"));
SettingPanel.setLayout(null);
SettingPanel.setBackground(Color.white);
SettingPanel.setBounds(20,120,1100,500);


usernm=new JLabel("Username");
usernm.setBounds(300,110,300,30);
usernm.setFont(new Font("Courier",Font.BOLD,20));
usertxt=new JTextField(10);
usertxt.setBounds(550,110,250,30);
usertxt.setFont(new Font("Courier",Font.PLAIN,18));

pass=new JLabel("Old Password");
pass.setBounds(300,150,300,30);
pass.setFont(new Font("Courier",Font.BOLD,20));
passtxt=new JPasswordField(8);
passtxt.setBounds(550,150,250,30);
passtxt.setFont(new Font("Courier",Font.PLAIN,18));

JLabel newpass=new JLabel("Create Password");
newpass.setBounds(300,190,300,30);
newpass.setFont(new Font("Courier",Font.BOLD,20));
newpasstxt=new JPasswordField(8);
newpasstxt.setBounds(550,190,250,30);
newpasstxt.setFont(new Font("Courier",Font.PLAIN,18));

cpass=new JLabel("Confirm Password");
cpass.setBounds(300,230,300,30);
cpass.setFont(new Font("Courier",Font.BOLD,20));
cpasstxt=new JPasswordField(8);
cpasstxt.setBounds(550,230,250,30);
cpasstxt.setFont(new Font("Courier",Font.PLAIN,18));


cgpass=new JButton("Change Password");
cgpass.setFont(new Font("Courier",Font.BOLD,18));
cgpass.setBounds(350,320,200,40);
cgpass.setBackground(Color.black);
cgpass.setForeground(Color.white);
deluser=new JButton("Delete User");
deluser.setFont(new Font("Courier",Font.BOLD,18));
deluser.setBounds(580,320,200,40);
deluser.setBackground(DarkRed);



SettingPanel.add(usernm);
SettingPanel.add(usertxt);

SettingPanel.add(pass);
SettingPanel.add(passtxt);
SettingPanel.add(newpass);SettingPanel.add(newpasstxt);
SettingPanel.add(cpass);
SettingPanel.add(cpasstxt);

SettingPanel.add(cgpass);
SettingPanel.add(deluser);
cgpass.addActionListener(this);
deluser.addActionListener(this);

//-------------------------------------------------------------------------------
mpanel.add(medicPanel,"MedicinesCard");
mpanel.add(supplierPanel,"SuppliersCard");
mpanel.add(salesPanel,"SalesCard");
mpanel.add(addproPanel,"AddProductCard");
mpanel.add(SettingPanel,"SettingsCard");
add(mpanel);
mpanel.setVisible(false);

/////////////////////////////////////////////////////////Images///////////////////////////////////
ImageIcon medLogo=new ImageIcon("logo1.jpg");
JLabel logo1=new JLabel(medLogo);
logo1.setBounds(20,0,140,119);
add(logo1);
ImageIcon heart=new ImageIcon("logo3.jpg");
JLabel logo3=new JLabel(heart);
logo3.setBounds(1200,640,160,53);
add(logo3);
try{
	Class.forName("com.mysql.cj.jdbc.Driver");
	Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/nikita","root","root");
	Statement st=con.createStatement();
	ResultSet rs=st.executeQuery("select *from products");
	int rows=dtm1.getRowCount();
	for(int i=0;i<rows;i++)
		dtm1.removeRow(i);

	while(rs.next())
	{
	obj1[0] = rs.getInt(1);
	obj1[1] = rs.getString(2);
	obj1[2] = rs.getString(3);
	obj1[3] = rs.getDate(4);
	obj1[4] = rs.getString(5);
	obj1[5] = rs.getString(6);
	obj1[6] = rs.getInt(7);
	obj1[7] = rs.getInt(8);
	dtm1.addRow(obj1);
	}	
	st.close();
	con.close();
	}catch(Exception ex){}

	try{
	Class.forName("com.mysql.cj.jdbc.Driver");
	Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/nikita","root","root");
	Statement st=con.createStatement();
	ResultSet rs=st.executeQuery("select *from suppliers");
	int rows1=dtm2.getRowCount();
	for(int i=0;i<rows1;i++)
		dtm2.removeRow(i);

	while(rs.next())
	{
	obj2[0] = rs.getString(1);
	obj2[1] = rs.getLong(2);
	obj2[2] = rs.getString(3);
	obj2[3] = rs.getString(4);
	obj2[4] = rs.getString(5);
	dtm2.addRow(obj2);
	}	
	st.close();
	con.close();
	}catch(Exception ex){}
	
	
try{
	Class.forName("com.mysql.cj.jdbc.Driver");
	Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/nikita","root","root");
	Statement st=con.createStatement();
	ResultSet rs=st.executeQuery("select *from sales");
	int rows1=dtm3.getRowCount();
	for(int i=0;i<rows1;i++)
		dtm3.removeRow(i);

	while(rs.next())
	{
	obj3[0] = rs.getString(1);
	obj3[1] = rs.getString(2);
	obj3[2] = rs.getInt(3);
	obj3[3] = rs.getInt(4);
	obj3[4] = rs.getString(5);
	dtm3.addRow(obj3);
	}	
	st.close();
	con.close();
	}catch(Exception ex){}
}
	public void actionPerformed(ActionEvent e)
{
    if(e.getSource()==b1)
   {mpanel.setVisible(true);
    close.setVisible(true);
    purchasePanel.setVisible(false);
	AddSuppPanel.setVisible(false);
		
    c1.show(mpanel,"MedicinesCard");
	}
  if(e.getSource()==b2)
   {mpanel.setVisible(true);
     close.setVisible(true);
     purchasePanel.setVisible(false);
	   AddSuppPanel.setVisible(false);
     c1.show(mpanel,"SuppliersCard");
  }
  if(e.getSource()==b3)
   {mpanel.setVisible(true);
     close.setVisible(true);
     purchasePanel.setVisible(false);
	  AddSuppPanel.setVisible(false);
     c1.show(mpanel,"SalesCard");
  }
   if(e.getSource()==b4)
   {
	   mpanel.setVisible(true);
    close.setVisible(true);
     purchasePanel.setVisible(false);
	   AddSuppPanel.setVisible(false);
      c1.show(mpanel,"AddProductCard");
   }
  if(e.getSource()==b5)
   { mpanel.setVisible(true);
    close.setVisible(true);    
     purchasePanel.setVisible(false);
	   AddSuppPanel.setVisible(false);
      c1.show(mpanel,"SettingsCard");  
  }   
if(e.getSource()==close){
	mpanel.setVisible(false);
        purchasePanel.setVisible(true);
        close.setVisible(false);
      }
   if(e.getSource()==addprod)  
     {   
		 
         try{
			            String dt=t4.getText();
						java.util.Date date=new java.util.Date(dt) ;
						java.sql.Date sqldt=new java.sql.Date(date.getTime());
						
						Class.forName("com.mysql.cj.jdbc.Driver");
						Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/nikita","root","root");
						String sql="insert into products values(?,?,?,?,?,?,?,?)";
						PreparedStatement ps=con.prepareStatement(sql);
						ps.setInt(1,Integer.parseInt(t1.getText()));
						ps.setString(2,t2.getText());
						ps.setString(3,t3.getText());
						ps.setDate(4,sqldt);
						ps.setString(5,t5.getText());
						ps.setString(6,t6.getText());
						ps.setInt(7,Integer.parseInt(t7.getText()));
						ps.setInt(8,Integer.parseInt(t8.getText()));
						ps.executeUpdate();
						Statement st=con.createStatement();
						ResultSet rs=st.executeQuery("select *from products");
						if(rs.last())
						{
							obj1[0] = rs.getInt(1);
							obj1[1] = rs.getString(2);
							obj1[2] = rs.getString(3);
							obj1[3] = rs.getDate(4);
							obj1[4] = rs.getString(5);
							obj1[5] = rs.getString(6);
							obj1[6] = rs.getInt(7);
							obj1[7] = rs.getInt(8);
							dtm1.addRow(obj1);
						}	
						st.close();
						ps.close();
						con.close();
						
						
			}catch(Exception e2){}
		 
		 
		 
		 
		 
		 
     }
if(e.getSource()==addsupp)
{
mpanel.setVisible(false);
AddSuppPanel.setVisible(true);
}

if(e.getSource()==saveSupp1)
{
						
				try{	
						Class.forName("com.mysql.cj.jdbc.Driver");
						Connection con1=DriverManager.getConnection("jdbc:mysql://localhost:3306/nikita","root","root");
						String sql1="insert into suppliers values(?,?,?,?,?)";
						PreparedStatement ps1=con1.prepareStatement(sql1);
						ps1.setString(1,st1.getText());
						ps1.setLong(2,Long.parseLong(st2.getText()));
						ps1.setString(3,st3.getText());
						ps1.setString(4,st4.getText());
						ps1.setString(5,st5.getText());
						ps1.executeUpdate();
						System.out.println("INSERTED");
						Statement st1=con1.createStatement();
						ResultSet rs1=st1.executeQuery("select *from suppliers");
						if(rs1.last())
						{
							obj2[0] = rs1.getString(1);
							obj2[1] = rs1.getLong(2);
							obj2[2] = rs1.getString(3);
							obj2[3] = rs1.getString(4);
							obj2[4] = rs1.getString(5);
							dtm2.addRow(obj2);
						}	
						st1.close();
						ps1.close();
						con1.close();	
							
				JOptionPane.showMessageDialog(getContentPane(),"Supplier Record Added !");	
						
			}catch(Exception e2){}
		 
		

 }
 if(e.getSource()==delprod)
 {
	 int i=medicTable.getSelectedRow();
	 System.out.println(i);
	 int del=Integer.parseInt(dtm1.getValueAt(i,0).toString());
	 System.out.println(del);
	 if(i>=0)
	 {
		 dtm1.removeRow(i);
		try
		{
						Class.forName("com.mysql.cj.jdbc.Driver");
						Connection con1=DriverManager.getConnection("jdbc:mysql://localhost:3306/nikita","root","root");
						String sql1=("delete from products where Product_ID="+del);
						PreparedStatement ps1=con1.prepareStatement(sql1);
						ps1.executeUpdate();
						JOptionPane.showMessageDialog(getContentPane(),"Product Record Deleted !");	
		}catch(Exception de){}
	 }
 }

 if(e.getSource()==delsupp)
 {
	 int i=supplierTable.getSelectedRow();
	 System.out.println(i);
	 String del=dtm2.getValueAt(i,0).toString();
	 System.out.println(del);
	 if(i>=0)
	 {
		 dtm2.removeRow(i);
		try
		{
						Class.forName("com.mysql.cj.jdbc.Driver");
						Connection con1=DriverManager.getConnection("jdbc:mysql://localhost:3306/nikita","root","root");
						String sql1=("delete from suppliers where name='"+del+"' ");
						PreparedStatement ps1=con1.prepareStatement(sql1);
						ps1.executeUpdate();
						JOptionPane.showMessageDialog(getContentPane(),"Supplier Record Deleted !");	
		}catch(Exception de){}
	 }
	 
 }

	if(e.getSource()==srchbtn)
	{   
		int flag=0; //searchbar,pname,pqty,pprice,pexp;
			try {    
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con3=DriverManager.getConnection("jdbc:mysql://localhost:3306/nikita","root","root");
					Statement st=con3.createStatement();
					ResultSet rs=st.executeQuery("select Product_Name from products");
					
					while(rs.next())
						{
							if(searchbar.getText().equals(rs.getString("Product_Name")))
						  { 
							flag=1;
							break;							
						  }								
						}
					
                	st.close();
					con3.close();
			    		
		}catch(Exception e3){}
	
		if(flag==1)
		{			
			try {    
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con3=DriverManager.getConnection("jdbc:mysql://localhost:3306/nikita","root","root");
					Statement st=con3.createStatement();
					ResultSet rs=st.executeQuery("select Product_Name,Expiry_Date,Price from products where Product_Name='"+searchbar.getText()+"' ");
					
					while(rs.next())
					{
							pname.setText(rs.getString("Product_Name"));
							pexp.setText(""+rs.getDate("Expiry_Date"));
							pprice.setText(""+rs.getInt("Price"));
						  
							
						
					}
				}catch(Exception ep){}
		}
		else{
					pname.setText("");
					pexp.setText("");
					pprice.setText("");
					pqty.setText("");
					JOptionPane.showMessageDialog(getContentPane(),"Product Not Available ");
			}
	}
	if(e.getSource()==addp)
	{	
		java.text.DateFormat df=new java.text.SimpleDateFormat("dd/MM/yy HH:mm:ss");
		java.util.Date dt=new java.util.Date();
		String currdate=(df.format(dt)).toString();
		obj4[0]=pname.getText();
		obj4[1]=pexp.getText();
		obj4[2]=pqty.getText();
		obj4[3]=pprice.getText();
		obj4[4]=currdate;
		dtm4.addRow(obj4);
		total=total+(Integer.parseInt(pprice.getText())*Integer.parseInt(pqty.getText()));
		totp.setText(distot+total);
			try{
			            Class.forName("com.mysql.cj.jdbc.Driver");
						Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/nikita","root","root");
						String sql="insert into sales values(?,?,?,?,?)";
						PreparedStatement ps=con.prepareStatement(sql);
						ps.setString(1,pname.getText());
						ps.setString(2,pexp.getText());
						ps.setInt(3,Integer.parseInt(pqty.getText()));
						ps.setInt(4,total);
						ps.setString(5,currdate);
						ps.executeUpdate();
						System.out.println("Inserted");
						Statement st=con.createStatement();
						ResultSet rs=st.executeQuery("select *from sales");
						if(rs.last())
						{
							obj3[0] = rs.getString(1);
							obj3[1] = rs.getString(2);
							obj3[2] = rs.getInt(3);
							obj3[3] = rs.getInt(4);
							obj3[4] = rs.getString(5);
							dtm3.addRow(obj3);
						}	
						st.close();
						ps.close();
						con.close();
						
						
			}catch(Exception e2){}
	
	}
	
	
	// }se
	if(e.getSource()==logout)
	{
		setVisible(false);
		total=0;
		Medical l=new Medical();
		l.setVisible(true);
	}
	
	
		
	
}
}
class Medical extends JFrame implements ActionListener
{
JButton logbut;
JLabel user,pass,logimg;
ImageIcon lgnicon;
JTextField u;
JPanel login;
JPasswordField p;
Medical()
{
setSize(1400,735);
setLayout(null);
setDefaultCloseOperation(EXIT_ON_CLOSE);
login=new JPanel();
login.setBounds(450,60,500,500);
login.setLayout(null);
login.setBorder(BorderFactory.createTitledBorder("LOGIN"));
login.setBackground(Color.white);
lgnicon=new ImageIcon("login.jpg");
logimg=new JLabel(lgnicon);
logimg.setBounds(160,20,200,177);
user=new JLabel("Username");
user.setFont(new Font("Courier",Font.BOLD,25));
user.setBounds(40,200,150,30);
u=new JTextField(15);
u.setFont(new Font("Courier",Font.BOLD,20));
u.setBounds(190,200,150,30);
u.setSize(270,40);
pass=new JLabel("Password");
pass.setFont(new Font("Courier",Font.BOLD,25));
pass.setBounds(40,280,150,30);
p=new JPasswordField(15);
p.setFont(new Font("Courier",Font.BOLD,20));
p.setBounds(190,280,150,30);
p.setSize(270,40);
logbut=new JButton("LOGIN");
logbut.setFont(new Font("Courier",Font.BOLD,20));
logbut.setBounds(150,400,150,30);
logbut.setSize(200,50);
logbut.setBackground(Color.black);
logbut.setForeground(Color.white);
login.add(logimg);
login.add(user);
login.add(u);
login.add(pass);
login.add(p);
login.add(logbut);
add(login);
logbut.addActionListener(this);
}
public void actionPerformed(ActionEvent e)
{  
	if((u.getText().equals("Admin") && p.getText().equals("admin123")))
	{				 
				setVisible(false);
			    MenuPage m1=new MenuPage();
				m1.setVisible(true);
	}
	else{
				 p.setText("");	
				 JOptionPane.showMessageDialog(getContentPane(),"Incorrect Username or Password","alert",JOptionPane.ERROR_MESSAGE);
		} 						
}

public static void main(String args[])
{
Medical l=new Medical();
l.setVisible(true);
}
}
