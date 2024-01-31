package data;

import lombok.Getter;

@Getter
public enum CarriersData {
  TK("TK"),
  S7("S7"),
  SU("SU"),
  BA("BA");

  private final String name;

  CarriersData(String name) {
    this.name = name;
  }

}
