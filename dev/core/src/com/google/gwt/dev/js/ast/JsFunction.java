/*
 * Copyright 2007 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.google.gwt.dev.js.ast;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a JavaScript function expression.
 */
public class JsFunction extends JsExpression implements HasName {

  protected JsBlock body;
  protected final List<JsParameter> params = new ArrayList<JsParameter>();
  protected final JsScope scope;
  private JsName name;

  /**
   * Creates an anonymous function.
   */
  public JsFunction(JsScope parent) {
    this(parent, null);
  }

  /**
   * Creates an named function.
   */
  public JsFunction(JsScope parent, JsName name) {
    assert (parent != null);
    this.name = name;
    String scopeName = (name == null) ? "<anonymous>" : name.getIdent();
    scopeName = "function " + scopeName;
    this.scope = new JsScope(parent, scopeName);
  }

  public JsBlock getBody() {
    return body;
  }

  public JsName getName() {
    return name;
  }

  public List<JsParameter> getParameters() {
    return params;
  }

  public JsScope getScope() {
    return scope;
  }

  public void setBody(JsBlock body) {
    this.body = body;
  }

  public void setName(JsName name) {
    this.name = name;
  }

  public void traverse(JsVisitor v, JsContext<JsExpression> ctx) {
    if (v.visit(this, ctx)) {
      v.acceptWithInsertRemove(params);
      body = v.accept(body);
    }
    v.endVisit(this, ctx);
  }
}
