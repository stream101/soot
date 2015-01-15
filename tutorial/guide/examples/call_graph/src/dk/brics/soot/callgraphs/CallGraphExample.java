package dk.brics.soot.callgraphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import soot.MethodOrMethodContext;
import soot.PackManager;
import soot.Scene;
import soot.SceneTransformer;
import soot.SootClass;
import soot.SootMethod;
import soot.Transform;
import soot.jimple.toolkits.callgraph.CHATransformer;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.jimple.toolkits.callgraph.Targets;
import soot.options.Options;
import java.io.File;

public class CallGraphExample
{	
	public static void main(String[] args) {
	   List<String> argsList = new ArrayList<String>(Arrays.asList(args));

	   argsList.addAll(Arrays.asList(new String[]{
			   "-w",
			   "-main-class",
			   "testers.CallGraphs",//main-class
			   "testers.CallGraphs",//argument classes
			   "testers.A"			//
	   }));
	
	   PackManager.v().getPack("wjtp").add(new Transform("wjtp.myTrans", new SceneTransformer() {

		@Override
		protected void internalTransform(String phaseName, Map options) {
			  System.out.println("soot path: " + Scene.v().getSootClassPath());
				
				//Builds an invoke graph using Class Hierarchy Analysis
		       CHATransformer.v().transform();
              
               //Return the SootClass with class name  
			   SootClass a = Scene.v().getSootClass("testers.A");
					
			   //retrieve the method src with name	
		       SootMethod src = Scene.v().getMainClass().getMethodByName("doStuff");
			   // build call graph for target classes
		       CallGraph cg = Scene.v().getCallGraph();

			   //edgesOutOf return an iterator over all edges that have 'src' as
			   //their source method 
			   //Adapts an iterator over a collection of Edge's
		       Iterator<MethodOrMethodContext> targets = new Targets(cg.edgesOutOf(src));
		       while (targets.hasNext()) {
		           SootMethod tgt = (SootMethod)targets.next();
		           System.out.println(src + " may call " + tgt);
		       }
		}
		   
	   }));

           args = argsList.toArray(new String[0]);
		   
           soot.Main.main(args);
	}
}
