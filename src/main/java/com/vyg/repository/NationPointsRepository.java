package com.vyg.repository;

import com.vyg.entity.NationPoints;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NationPointsRepository extends JpaRepository<NationPoints, Long> {


//    @Query("SELECT new com.vyg.model.NationPointsDTO(" +
//            "CAST(np.nation AS string), " +
//            "np.weekNumber, " +
//            "FUNCTION('MONTHNAME', cp.dateCaptured), " +
//            "cp.year, " +
//            "SUM(np.pointsEarned)) " +
//            "FROM NationPoints np " +
//            "JOIN CapturedPoint cp ON cp.nation.nation = np.nation " +
//            //"WHERE cp.nation. address.id = :addressId " +
//            "WHERE cp.nation.nation"
//            "GROUP BY np.nation, np.weekNumber, FUNCTION('MONTHNAME', cp.dateCaptured), cp.year " +
//            "ORDER BY cp.year DESC, np.weekNumber DESC")
//    List<NationPointsDTO> getNationPointsSummaryByAddressId(@Param("addressId") Long addressId);
//


}
