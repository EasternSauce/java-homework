package votegui.voteapp;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

public class VoteModel{
	public boolean placeVote(String voter, String[] candidates, String[] weights, String system, boolean xml){
		File unsecured = new File("unsecured");
		String ballot_name = "unsecured/ballot";
		ballot_name += unsecured.listFiles().length;
		ballot_name += ".txt";

		if(xml){
			try{
				Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
				Element root = doc.createElement("root");
				Element sysNode = doc.createElement("system");
				Element voterNode = doc.createElement("voter");
				sysNode.setTextContent(system);
				root.appendChild(sysNode);
				voterNode.setTextContent(voter);
				root.appendChild(voterNode);
				
				Element candsNode = doc.createElement("votes");
				root.appendChild(candsNode);
				for(int i = 0; i < 5; i++){
					if(candidates[i] != null && !candidates[i].isEmpty()){
						Element candNode = doc.createElement("candidate");
						candNode.setTextContent(candidates[i]);
						if(weights[i] != null && !weights[i].isEmpty()){
							candNode.setAttribute("weight", weights[0]);
						}
						else{
							candNode.setAttribute("weight", "0");
						}
						candsNode.appendChild(candNode);
					}
				}
				doc.appendChild(root);

				Transformer newTransformer = TransformerFactory.newInstance().newTransformer();
				DOMSource src = new DOMSource(doc);
				File file = new File(ballot_name);
				StreamResult rs = new StreamResult(file);
				newTransformer.setOutputProperty(OutputKeys.INDENT, "yes");
				newTransformer.transform(src, rs);

			}catch(Exception e){
				System.out.println("Problem with DOM making an XML file");
			}
		}
		else{
			try{
				PrintWriter writer = new PrintWriter(ballot_name, "UTF-8");
				writer.println(system);
				writer.println("I am");
				writer.println(voter);
				writer.println("And I vote for");
				for(int i = 0; i < 5; i++){
					if(candidates[i] != null && !candidates[i].isEmpty()){
						writer.println(candidates[i]);
					}
					if(weights[i] != null && !weights[i].isEmpty()){
						writer.println(weights[i]);
					}
				}

				writer.close();

			}catch(FileNotFoundException ex){
				return false;
			}catch(UnsupportedEncodingException ex){
				return false;
			}
		}

		return true;
	}
}
