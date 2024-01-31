package data;

import lombok.Getter;

@Getter
public enum AirportsData {
  VVO("Владивосток"),
  TLV("Тель-Авив"),
  UFA("Уфа"),
  LRN("Ларнака");

  private final String name;

  AirportsData(String name) {
    this.name = name;
  }
}
