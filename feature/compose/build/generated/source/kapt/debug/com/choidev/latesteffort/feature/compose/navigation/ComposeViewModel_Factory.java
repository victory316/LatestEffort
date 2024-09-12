package com.choidev.latesteffort.feature.compose.navigation;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class ComposeViewModel_Factory implements Factory<ComposeViewModel> {
  @Override
  public ComposeViewModel get() {
    return newInstance();
  }

  public static ComposeViewModel_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static ComposeViewModel newInstance() {
    return new ComposeViewModel();
  }

  private static final class InstanceHolder {
    private static final ComposeViewModel_Factory INSTANCE = new ComposeViewModel_Factory();
  }
}
