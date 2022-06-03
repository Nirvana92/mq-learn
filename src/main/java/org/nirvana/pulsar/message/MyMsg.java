package org.nirvana.pulsar.message;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Nirvana
 */
@Data
public class MyMsg implements Serializable {

    private String message;
}
