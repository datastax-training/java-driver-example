import com.datastax.driver.core.*;
import com.datastax.driver.core.policies.DowngradingConsistencyRetryPolicy;
import com.datastax.driver.core.policies.LoggingRetryPolicy;
import com.datastax.driver.core.policies.TokenAwarePolicy;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;

import static com.datastax.driver.core.querybuilder.QueryBuilder.*;


import java.util.Date;
import java.util.UUID;


public class DataStaxDriver {
   private static String clusterAddress = "127.0.0.1";
   Cluster cluster;

   public Session connect() {
      cluster = Cluster
              .builder()
              .addContactPoint(clusterAddress)
              .build();
      return cluster.connect("killr_video");
   }


   public void hardCodedInsert(Session session) {
      session.execute("INSERT INTO users ( user_id, created_date, email, first_name, last_name) VALUES (14c532ac-f5ae-479a-9d0a-36604732e01d, '2015-07-22 00:00:00', 'tim.berglund@datastax.com', 'Tim', 'Berglund')");
   }


   public void preparedInsert(Session session,
                              String email,
                              String firstName,
                              String lastName) {
      PreparedStatement smt = session.prepare("INSERT INTO users (user_id, created_date, email, first_name, last_name) VALUES (?, ?, ?, ?, ?)");

      BoundStatement bound = new BoundStatement(smt);
      BoundStatement bs = bound.bind(UUID.randomUUID(), new Date(),email, firstName, lastName);
      session.execute(bs);
   }


   public void printUsers(Session session) {
      Statement allUsers = QueryBuilder.select().all().from("killr_video", "users");
      ResultSet rs = session.execute(allUsers);
      for(Row row : rs) {
         System.out.printf("%s %s %s\n",
                           row.getString("email"),
                           row.getString("first_name"),
                           row.getString("last_name"));
      }
   }


   public static void main(String args[]) {
      DataStaxDriver d = new DataStaxDriver();
      Session session = d.connect();
      d.preparedInsert(session, "tim@timberglund.com", "Tim", "Berglund");

      d.printUsers(session);
   }
}
