package vm.example.solarStatistic.util.db;

import com.opentable.db.postgres.embedded.EmbeddedPostgres;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.TimeZone;

@TestConfiguration
public class EmbeddedDatabaseConfiguration {

    @PostConstruct
    private void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    @Bean
    @Primary
    public DataSource inMemoryDS() throws Exception {
        DataSource embeddedPostgresDS = EmbeddedPostgres.builder()
                .start().getPostgresDatabase();

        return embeddedPostgresDS;
    }
}
