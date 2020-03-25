package vm.example.solarStatistic.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vm.example.solarStatistic.model.solardb.SolarPark;

@Repository
public interface SolarParkRepository extends JpaRepository<SolarPark, Long> {
    public SolarPark getSolarParkById(Long id);
}
