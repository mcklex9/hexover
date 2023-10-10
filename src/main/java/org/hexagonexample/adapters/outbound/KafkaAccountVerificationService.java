package org.hexagonexample.adapters.outbound;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.hexagonexample.application.ports.outbound.SoftCheckVerificationService;
import org.hexagonexample.domain.Account;
import org.hexagonexample.domain.AccountId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;

@RequiredArgsConstructor
public class KafkaAccountVerificationService implements SoftCheckVerificationService {

    private final KafkaTemplate kafkaTemplate;

    @Value("$services.accountVerification.topicName")
    private String accountVerificationTopicName;

    @Override
    public void softCheckAccount(AccountId accountId) {
        KafkaSoftCheckAccountCommand kafkaSoftCheckAccountCommand = new KafkaSoftCheckAccountCommand(accountId);
        ProducerRecord<AccountId, KafkaSoftCheckAccountCommand> producerRecord = new ProducerRecord<>(accountVerificationTopicName, accountId, kafkaSoftCheckAccountCommand);
        kafkaTemplate.send(producerRecord);
    }
}
