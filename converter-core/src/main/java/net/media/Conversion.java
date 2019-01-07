package net.media;

import net.media.enums.OpenRtbType;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author shiva.b
 */
@Data
@AllArgsConstructor
public class Conversion {

  private Class source;

  private Class target;
}
