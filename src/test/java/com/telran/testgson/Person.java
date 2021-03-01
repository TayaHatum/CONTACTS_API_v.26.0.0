package com.telran.testgson;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString

public class Person {
    String name;
    int age;
    Address address;
    String[] phones;
    boolean isMarried;
    String group;
}
