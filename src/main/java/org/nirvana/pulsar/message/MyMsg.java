package org.nirvana.pulsar.message;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Nirvana
 */
@Builder
@Data
public class MyMsg implements Serializable {

    private String message;
}
