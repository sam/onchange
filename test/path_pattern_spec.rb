#!/usr/bin/env jruby

# encoding: utf-8

require_relative "helper"

java_import org.substantiality.PathPattern
#
describe PathPattern do
  it "must parse root from pattern" do
    path = PathPattern.new "ext/java/**/*.java"
    path.root.to_s.must_equal "ext/java"
  end

  describe "matching" do
    before do
      @pattern = PathPattern.new "ext/java/**/*.java"
    end

    it "must match" do
      @pattern.match("ext/java/OnChange.java").must_equal true
      @pattern.match("ext/java/org/substantiality/PathPattern.java").must_equal true
    end

    it "wont match" do
      @pattern.match("test/api_spec.rb").wont_equal true
      @pattern.match("ext/java/org/substantiality/PathPattern.rb").wont_equal true
    end
  end
end
