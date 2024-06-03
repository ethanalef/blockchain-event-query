package org.tron.utility.blockchain;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.tron.utility.blockchain.BlockchainApplication;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = BlockchainApplication.class)
@ActiveProfiles("prod")
public abstract class BaseTest {
}