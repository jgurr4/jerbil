package com.ple.jerbil.data;

public enum ReferenceOption {
  cascade,
  restrict,  //Same as omitting onDelete or onUpdate
  setNull
}
