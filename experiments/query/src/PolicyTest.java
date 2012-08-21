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
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;

public class PolicyTest {

  public static void queryAll(Model model) {
    String query = getQuery("resources/query_all.txt");
    System.out.println("######### ALL #########");
    QueryExecution qe = QueryExecutionFactory.create(query, model); // getBaseModel?
    ResultSet set = qe.execSelect();

    while (set.hasNext()) {
      QuerySolution next = set.nextSolution();

      System.out.println(next.toString());
    }
  }

  public static void queryAllPropertiesForScenario(Model model) {
    System.out.println("######### ALL PROPERTIES FOR SCENARIO ASA_MINISTRIES_SCANNED_PAPERS_SCENARIO #########");

    String query = getQuery("resources/query_all_props_for_scenario.txt");

    QueryExecution qe = QueryExecutionFactory.create(query, model); // getBaseModel?
    ResultSet set = qe.execSelect();

    while (set.hasNext()) {
      QuerySolution next = set.nextSolution();
      Literal prop = next.getLiteral("pName");
      System.out.println("Property: " + prop.getString());
    }
  }

  public static void queryAllFormatObjectivesForOrganization(Model model) {
    System.out.println("######### ALL FORMAT OBJECTIVES FOR ORGANIZATION AUSTRIAN STATE ARCHIVES #########");

    String query = getQuery("resources/query_all_format_objectives_for_organization.txt");

    QueryExecution qe = QueryExecutionFactory.create(query, model); // getBaseModel?
    ResultSet set = qe.execSelect();

    while (set.hasNext()) {
      QuerySolution next = set.nextSolution();
      Resource obj = next.getResource("objective");
      Resource measure = next.getResource("measure");
      Literal prop = next.getLiteral("desc");
      Resource mod = next.getResource("modality");
      Literal val = next.getLiteral("value");
      System.out.println("Format Objective [" + obj.toString() + "] defines that: \nproperty '" + prop.getString()
          + "' " + mod.getLocalName() + " have a \nvalue '" + val.getString() + "'\n");
    }
  }

  public static void queryAllFormatObjectivesForScenario(Model model) {
    System.out.println("######### ALL FORMAT OBJECTIVES FOR SCENARIO DOCUMENT EXECUTIONS #########");

    String query = getQuery("resources/query_all_format_objectives_for_scenario.txt");

    QueryExecution qe = QueryExecutionFactory.create(query, model); // getBaseModel?
    ResultSet set = qe.execSelect();

    while (set.hasNext()) {
      QuerySolution next = set.nextSolution();
      Resource obj = next.getResource("objective");
      Literal prop = next.getLiteral("label");
      Resource mod = next.getResource("modality");
      Literal val = next.getLiteral("value");
      Resource metric = next.getResource("metric");
      System.out.println("Format Objective [" + obj.toString() + "] defines that: \nproperty '" + prop.getString()
          + "' " + mod.getLocalName() + " have a \nvalue " + getMetric(metric) + " '" + val.getString() + "'\n");
    }

  }

  public static void main(String... args) {
    OntModelSpec spec = OntModelSpec.OWL_MEM_RDFS_INF;
    OntModel ont = ModelFactory.createOntologyModel(spec);
    ont.read("file:../../refactored/pw.rdf");
    ont.read("file:../../refactored/attributes_measures.rdf");
    ont.read("file:../../refactored/asa_individuals.rdf");
    Model base = ont.getBaseModel();
    
//    queryAll(base);
    queryAllFormatObjectivesForOrganization(base);
    queryAllFormatObjectivesForScenario(base);
    queryAllPropertiesForScenario(base);
  }

  private static String getQuery(String filepath) {
    String query = "";
    try {
      query = IOUtils.toString(new FileInputStream(filepath));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return query;
  }

  private static String getMetric(Resource metric) {
    String result = "";
    if (metric != null) {
      result = metric.getLocalName();
    }

    return result;
  }

}
