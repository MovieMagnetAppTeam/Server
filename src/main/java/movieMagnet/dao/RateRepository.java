package movieMagnet.dao;

import movieMagnet.model.Rate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RateRepository extends JpaRepository<Rate, Long> {

    @Override
    void delete(Rate rate);
}
