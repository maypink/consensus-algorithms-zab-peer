// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: zab_peer.proto

package zab_peer;

public interface FollowerInfoResponseOrBuilder extends
    // @@protoc_insertion_point(interface_extends:zab_peer.FollowerInfoResponse)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>.zab.ZxId last_zx_id = 1;</code>
   * @return Whether the lastZxId field is set.
   */
  boolean hasLastZxId();
  /**
   * <code>.zab.ZxId last_zx_id = 1;</code>
   * @return The lastZxId.
   */
  zab.ZxId getLastZxId();
  /**
   * <code>.zab.ZxId last_zx_id = 1;</code>
   */
  zab.ZxIdOrBuilder getLastZxIdOrBuilder();

  /**
   * <code>.zab.Trunc trunc = 2;</code>
   * @return Whether the trunc field is set.
   */
  boolean hasTrunc();
  /**
   * <code>.zab.Trunc trunc = 2;</code>
   * @return The trunc.
   */
  zab.Trunc getTrunc();
  /**
   * <code>.zab.Trunc trunc = 2;</code>
   */
  zab.TruncOrBuilder getTruncOrBuilder();

  /**
   * <code>.zab.Diff diff = 3;</code>
   * @return Whether the diff field is set.
   */
  boolean hasDiff();
  /**
   * <code>.zab.Diff diff = 3;</code>
   * @return The diff.
   */
  zab.Diff getDiff();
  /**
   * <code>.zab.Diff diff = 3;</code>
   */
  zab.DiffOrBuilder getDiffOrBuilder();

  /**
   * <code>.zab.Snap snap = 4;</code>
   * @return Whether the snap field is set.
   */
  boolean hasSnap();
  /**
   * <code>.zab.Snap snap = 4;</code>
   * @return The snap.
   */
  zab.Snap getSnap();
  /**
   * <code>.zab.Snap snap = 4;</code>
   */
  zab.SnapOrBuilder getSnapOrBuilder();

  public zab_peer.FollowerInfoResponse.TypeCase getTypeCase();
}