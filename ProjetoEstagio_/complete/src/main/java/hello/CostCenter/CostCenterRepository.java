package hello.CostCenter;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CostCenterRepository extends JpaRepository<CostCenter, Long> {
    CostCenter findById(long id);
}