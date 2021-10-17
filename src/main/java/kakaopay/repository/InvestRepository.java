package kakaopay.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import kakaopay.entity.Invest;

@Repository
public interface InvestRepository extends JpaRepository<Invest, Integer> {
    public List<Invest> findByUserId(@Param(value = "userId") int userId);

    @Query(value = 
    "SELECT SUM(invest.amount) as TotalAmount, COUNT(invest.user_id) AS UserCnt FROM invest where invest.product_id = ?1",
    nativeQuery = true)
    List<Object[]> findInvestInfo(int product_id);
}
