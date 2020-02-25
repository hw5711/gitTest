package calendarProject;import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.util. Hashtable;

import java.io.*;

public class notePad extends JPanel implements ActionListener
{
	JTextArea text;
	JButton saveNote, deleteNote;
	Hashtable<String,String> table= new Hashtable<String,String>();//
	JLabel message;
	int noteYear,noteMonth,noteDay;
	File file;
	myCalendar calendar;
	
	public notePad(myCalendar calendar)
	{
		this.calendar=calendar;
		noteYear=calendar.getYear();
		noteMonth=calendar.getMonth();
		noteDay=calendar.getDay();
		table=calendar.getHashtable();
		file=calendar.getFile();
		message=new JLabel(""+noteYear+"/"+noteMonth+"/"+noteDay,JLabel.CENTER);
		message.setFont(new Font("Serif",Font.BOLD,16));
		message.setForeground(Color.blue);
		text=new JTextArea(12,10);
		saveNote=new JButton("Save Note");
		deleteNote=new JButton("Delete Note");
		saveNote.addActionListener(this);
		deleteNote.addActionListener(this);
		setLayout(new BorderLayout());
		JPanel pSouth=new JPanel();
		add(message,BorderLayout.NORTH);
		pSouth.add(saveNote);
		pSouth.add(deleteNote);
		add(pSouth,BorderLayout.SOUTH);
		add(new JScrollPane(text),BorderLayout.CENTER);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==saveNote)
		{
			saveNote(noteYear,noteMonth,noteDay);
		}
		else if(e.getSource()==deleteNote)
		{
			deleteNote(noteYear,noteMonth,noteDay);
		}
	}
	
	public void setYear(int year)
	{
		this.noteYear=year;
	}
	
	public int getYear()
	{
		return noteYear;
	}
	
	public void setMonth(int month)
	{
		this.noteMonth=month;
	}
	
	public int getMonth()
	{
		return noteMonth;
	}
	
	public void setDay(int day)
	{
		this.noteDay=day;
	}
	
	public int getDay()
	{
		return noteDay;
	}
	
	public void setMessage(int noteYear, int noteMonth, int noteDay)
	{
		message.setText(""+noteYear+"/"+noteMonth+"/"+noteDay);
	}
	
	public void setNote(String s)
	{
		text.setText(s);
	}
	
	public void getNote(int noteYear, int noteMonth, int noteDay)
	{
		String key=""+noteYear+"/"+noteMonth+"/"+noteDay;
		try
		{
			FileInputStream inOne=new FileInputStream(file);
			ObjectInputStream inTwo=new ObjectInputStream(inOne);
			table=(Hashtable)inTwo.readObject();
			inOne.close();
			inTwo.close();
		}
		catch(Exception ee)
		{
		}
		
		if(table.containsKey(key))
		{
			String m=""+noteYear+"/"+noteMonth+"/"+noteDay+"  Do you want to see the added note?";
			int ok=JOptionPane.showConfirmDialog(this,m,"ask",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
			if(ok==JOptionPane.YES_OPTION)
			{
				text.setText((String)table.get(key));
			}
			else
			{
				text.setText("");
			}
		}
		else
		{
			text.setText("No Record");
		}
	}
	
	public void saveNote(int noteYear, int noteMonth, int noteDay)
	{
		String noteContent=text.getText();
		String key=""+noteYear+"/"+noteMonth+"/"+noteDay;
		String m=""+noteYear+"/"+noteMonth+"/"+noteDay+" Do you want to save the note?";
		int ok=JOptionPane.showConfirmDialog(this, m,"ask",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
		if(ok==JOptionPane.YES_OPTION)
		{
			try
			{
				FileInputStream inOne=new FileInputStream(file);
				ObjectInputStream inTwo=new ObjectInputStream(inOne);
				table=(Hashtable)inTwo.readObject();
				inOne.close();
				inTwo.close();
				table.put(key, noteContent);//
				FileOutputStream out=new FileOutputStream(file);
				ObjectOutputStream objectOut=new ObjectOutputStream(out);
				objectOut.writeObject(table);
				objectOut.close();
				out.close();
			}
			catch(Exception ee)
			{
			}
		}
	}
	
	public void deleteNote(int year, int month , int day)
	{
		String key=""+year+""+month+""+day;
		if(table.containsKey(key))
		{
			String m="delete "+year+"/"+month+"/"+day+" note?";
			int ok=JOptionPane.showConfirmDialog(this, m,"ask",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
			if(ok==JOptionPane.YES_OPTION)
			{
				try
				{
					FileInputStream inOne=new FileInputStream(file);
					ObjectInputStream inTwo=new ObjectInputStream(inOne);
					table=(Hashtable)inTwo.readObject();
					inOne.close();
					inTwo.close();
					table.remove(key);
					FileOutputStream out=new FileOutputStream(file);
					ObjectOutputStream objectOut=new ObjectOutputStream(out);
					objectOut.writeObject(table);
					objectOut.close();
					out.close();
					text.setText(null);
				}
				catch(Exception ee)
				{
				}
			}
		}
		else
		{
			String m=""+year+"/"+month+"/"+day+": No record";
			JOptionPane.showMessageDialog(this, m,"Notice: ", JOptionPane.WARNING_MESSAGE);
		}
	}
}
