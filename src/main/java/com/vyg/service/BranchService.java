package com.vyg.service;

import com.vyg.entity.Province;
import com.vyg.model.BranchRequest;
import com.vyg.entity.Branch;
import com.vyg.entity.Region;
import com.vyg.repository.BranchRepository;
import com.vyg.repository.ProvinceRepository;
import com.vyg.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BranchService {

    private final BranchRepository branchRepository;
    private final ProvinceRepository provinceRepository;
    private final RegionRepository regionRepository;

    public BranchService(BranchRepository branchRepository, ProvinceRepository provinceRepository, RegionRepository regionRepository) {
        this.branchRepository = branchRepository;
        this.provinceRepository = provinceRepository;
        this.regionRepository = regionRepository;
    }

    public Branch createBranch(BranchRequest request) {
        // ✅ Find or Create Province
        Province province = provinceRepository.findByName(request.getProvinceName())
                .orElseGet(() -> {
                    log.info("Creating new Province: {}", request.getProvinceName());
                    Province newProvince = new Province();
                    newProvince.setName(request.getProvinceName());
                    return provinceRepository.save(newProvince);
                });

        // ✅ Find or Create Region under the Province
        Region region = regionRepository.findByNameAndProvince(request.getRegionName(), province)
                .orElseGet(() -> {
                    log.info("Creating new Region: {} under {}", request.getRegionName(), province.getName());
                    Region newRegion = new Region();
                    newRegion.setName(request.getRegionName());
                    newRegion.setProvince(province);
                    return regionRepository.save(newRegion);
                });

        // ✅ Create and Save the Branch
        Branch branch = Branch.builder()
                .churchName(request.getChurchName())
                .address(request.getAddress())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .region(region)  // ✅ Link Region (which links Province)
                .build();

        return branchRepository.save(branch);
    }
        public List<Branch> getBranches() {
            return branchRepository.findAll();  // ✅ Return a list
        }

}

