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

		       CHATransformer.v().transform();
               
			   //System.out.println("Soot class path " + Scene.v().getSootClassPath());
			   SootClass a = Scene.v().getSootClass("testers.A");

		       SootMethod src = Scene.v().getMainClass().getMethodByName("doStuff");
		       CallGraph cg = Scene.v().getCallGraph();

		       Iterator<MethodOrMethodContext> targets = new Targets(cg.edgesOutOf(src));
		       while (targets.hasNext()) {
		           SootMethod tgt = (SootMethod)targets.next();
		           System.out.println(src + " may call " + tgt);
		       }
		}
		   
	   }));

           args = argsList.toArray(new String[0]);
		   
		   System.out.println("soot path: "+Arrays.toString(args));           
           soot.Main.main(args);
	}
}
