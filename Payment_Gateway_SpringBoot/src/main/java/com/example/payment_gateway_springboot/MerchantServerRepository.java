package com.example.payment_gateway_springboot;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantServerRepository extends JpaRepository<MerchantServer, Long> {

    MerchantServer findByMerchantId(String merchantId);
    MerchantServer findMerchantIdByServerKey(String serverKey);
    boolean existsByServerKey(String serverKey);
}

