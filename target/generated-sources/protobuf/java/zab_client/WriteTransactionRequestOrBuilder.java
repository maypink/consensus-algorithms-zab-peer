// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: zab_client.proto

package zab_client;

public interface WriteTransactionRequestOrBuilder extends
    // @@protoc_insertion_point(interface_extends:zab_client.WriteTransactionRequest)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>.zab.BankTransaction transaction = 1;</code>
   * @return Whether the transaction field is set.
   */
  boolean hasTransaction();
  /**
   * <code>.zab.BankTransaction transaction = 1;</code>
   * @return The transaction.
   */
  zab.BankTransaction getTransaction();
  /**
   * <code>.zab.BankTransaction transaction = 1;</code>
   */
  zab.BankTransactionOrBuilder getTransactionOrBuilder();
}
