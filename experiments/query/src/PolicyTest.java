import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.IOUtils;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;

public class PolicyTest {

  public static void main(String... args) {
    OntModelSpec spec = OntModelSpec.OWL_MEM_RDFS_INF;
    OntModel ont = ModelFactory.createOntologyModel(spec);
    ont.read("file:../../pw.owl");
    ont.read("file:../../pw_individuals.rdf");
    ont.read("file:../../asa_individuals.rdf");

    String query1 = "";
    String query2 = "";
    String query3 = "";
    try {
      query1 = IOUtils.toString(new FileInputStream("resources/query_all_format_objectives_for_organization.txt"));
      query2 = IOUtils.toString(new FileInputStream("resources/query_all_props_for_scenario.txt"));
      query3 = IOUtils.toString(new FileInputStream("resources/query_all_format_objectives_for_scenario.txt"));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    System.out.println("######### ALL FORMAT OBJECTIVES FOR ORGANIZATION AUSTRIAN STATE ARCHIVES #########");
    QueryExecution qe = QueryExecutionFactory.create(query1, ont.getBaseModel());
    ResultSet set = qe.execSelect();

    while (set.hasNext()) {
      QuerySolution next = set.nextSolution();
      Resource obj = next.getResource("objective");
      Literal prop = next.getLiteral("pName");
      Resource mod = next.getResource("modality");
      Literal val = next.getLiteral("value");
      System.out.println("Format Objective [" + obj.toString() + "] defines that: \nproperty '" + prop.getString()
          + "' " + mod.getLocalName() + " have a \nvalue '" + val.getString() + "'\n");
    }

    System.out.println("######### ALL PROPERTIES FOR SCENARIO ASA_MINISTRIES_SCANNED_PAPERS_SCENARIO #########");
    qe = QueryExecutionFactory.create(query2, ont.getBaseModel());
    set = qe.execSelect();

    while (set.hasNext()) {
      QuerySolution next = set.nextSolution();
      Literal prop = next.getLiteral("pName");
      System.out.println("Property: " + prop.getString());
    }

    System.out.println("######### ALL FORMAT OBJECTIVES FOR SCENARIO DOCUMENT EXECUTIONS #########");
    qe = QueryExecutionFactory.create(query3, ont.getBaseModel());
    set = qe.execSelect();

    while (set.hasNext()) {
      QuerySolution next = set.nextSolution();
      Resource obj = next.getResource("objective");
      Literal prop = next.getLiteral("pName");
      Resource mod = next.getResource("modality");
      Literal val = next.getLiteral("value");
      Resource metric = next.getResource("metric");
      System.out.println("Format Objective [" + obj.toString() + "] defines that: \nproperty '" + prop.getString()
          + "' " + mod.getLocalName() + " have a \nvalue " + getMetric(metric) + " '" + val.getString() + "'\n");
    }
  }
  
  private static String getMetric(Resource metric) {
    String result = "";
    if (metric != null) {
      result = metric.getLocalName();
    }
    
    return result;
  }
}
