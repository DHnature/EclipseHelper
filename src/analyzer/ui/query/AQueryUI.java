package analyzer.ui.query;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import analyzer.AParticipantTimeLine;
import analyzer.ParticipantTimeLine;
import analyzer.query.ASelector;
import analyzer.query.AnAggregator;



public class AQueryUI extends JPanel implements KeyListener, ActionListener{

	private static final long serialVersionUID = 1L;
	private JTextArea outputArea;
	private JTextField inputArea;
	private JButton clear;
	
	private String query;
	private QueryParser parser;
	
	private static final ASelector selector=new ASelector();
	private static final AnAggregator aggregator=new AnAggregator();

	public AQueryUI(Map<String,ParticipantTimeLine> data) {
		outputArea=new JTextArea();
		inputArea=new JTextField();
		clear=new JButton("Clear");
		parser=new QueryParser(data);
		query="";

		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(600,500));
		prep();
	}

	

	private void prep() {
		outputArea.setPreferredSize(new Dimension(600,400));
		clear.setPreferredSize(new Dimension(100,50));
		outputArea.setEditable(false);
		
		inputArea.setPreferredSize(new Dimension(500,50));
		
		
		
		JPanel panel=new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(inputArea, BorderLayout.WEST);
		panel.add(clear,BorderLayout.EAST);
		
	
		this.add(panel,BorderLayout.NORTH);
		this.add(new JScrollPane(outputArea),BorderLayout.SOUTH);
	
		clear.addActionListener(this);
		inputArea.addKeyListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		inputArea.setText("");
		query="";

	}
	
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_ENTER) {
			query=inputArea.getText();
			parser.parse(query);
		}

	}
	
	@Override
	public void keyTyped(KeyEvent e) {}
	@Override
	public void keyReleased(KeyEvent e) {}
	
	public class QueryParser {
		
		
		private Map<String,ParticipantTimeLine> data;
		
		public QueryParser(Map<String, ParticipantTimeLine> data) {
			this.data=data;
			
		}
		
		
		public void parse(String query) {
			String[] q=query.split(" ");
			
			Object result=null;
			
			if(q[0].equalsIgnoreCase("Select")) {
				//select the dominant features at each time
				if(q[1].equalsIgnoreCase("dominant")) {
					
					//take all the participant to be ignore
					Set<String> ignoredSet=new HashSet<>();
					
					if(q.length>=3 && q[2].equalsIgnoreCase("ignore")) {
						for(int i=3;i<q.length;i++)
							ignoredSet.add(q[i].toLowerCase());
						
					}
					
					Map<String,Object[]> map=new HashMap<>();
					
					for(String k:this.data.keySet()) {
						if(!ignoredSet.contains(k.toLowerCase())) {
							map.put(k, selector.getDominance(this.data.get(k)));
							
						}
						
					}
					
					result=map;
				}
				
			}
			
			handleResult(result);
			
		}
		
		@SuppressWarnings("unchecked")
		private void handleResult(Object result) {
			if(result instanceof Map) {
				
				Map<String, Object[]> r=(Map<String,Object[]>)result;
				
				
				JTextArea output=AQueryUI.this.outputArea;
				output.setText("");
				
				output.append("---RESULTS---\n\n");
				output.append("---Dominance---\n");
				//clear the jpanels
				for(String k:r.keySet()) {
					output.append("\nParticipant "+k+"\n");
					
					List<Long> timestamp=(List<Long>)r.get(k)[0];
					List<String> dominant=(List<String>)r.get(k)[1];
					
					for(int i=0;i<timestamp.size();i++) {
						output.append("Time: "+timestamp.get(i)+": "+dominant.get(i)+"\n");
						
						
					}
					
				}
				
				
				
				
			}
			
		}
		
	}


	public static void main(String[] args) {
		//start data here
		ParticipantTimeLine t=new AParticipantTimeLine();

		t.setDebugList(new ArrayList<>(Arrays.asList(new Double[] {
				105d,20d,60d,90d,30d, 20d

		})));

		t.setInsertionList(new ArrayList<>(Arrays.asList(new Double[] {
				30d, 20d, 20d,100d, 10d, 30d

		})));


		t.setDeletionList(new ArrayList<>(Arrays.asList(new Double[] {
				20d, 10d, 20d, 0d, 20d, 10d

		})));


		t.setFocusList(new ArrayList<>(Arrays.asList(new Double[] {
				20d, 21d, 5d, 0d, 10d, 20d

		})));

		t.setRemoveList(
				new ArrayList<>(Arrays.asList(new Double[] {
						5d, 75d, 5d, 0d, 10d, 40d

				}))	);


		t.setNavigationList(new ArrayList<>(Arrays.asList(new Double[] {
				15d, 15d, 0d, 0d, 20d, 20d

		})));
		
		t.setPredictions(new ArrayList<>(Arrays.asList(new Integer[] {
				0, 0, 1, 1, 0, 0

		})));

		t.setPredictionCorrections(new ArrayList<>(Arrays.asList(new Integer[] {
				-1, 1, -1, 0, -1, 1

		})));
		
		t.setTimeStampList(new ArrayList<>(Arrays.asList(new Long[] {
				0l,32l,45l,65l,78l,90l
				
		})));

		Map<String, ParticipantTimeLine> data=new HashMap<>();
		data.put("1", t);
		data.put("2", t);
		
		
		JFrame frame=new JFrame("Query V1.0");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);

		frame.add(new AQueryUI(data));
		frame.pack();
		frame.setVisible(true);
	
	}

}
