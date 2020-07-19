// EIDER HELPER GENERATED BY EIDER AT 2020-07-19T20:38:47.448060Z. DO NOT MODIFY
package io.eider.util;

import java.lang.FunctionalInterface;

@FunctionalInterface
public interface IndexUpdateConsumer<T> {
  /**
   * Accepts an index update for the given offset and type<T> value
   */
  void accept(int offset, T t);
}
