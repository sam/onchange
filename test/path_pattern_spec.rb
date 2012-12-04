#!/usr/bin/env jruby

# encoding: utf-8

require_relative "helper"

java_import org.substantiality.PathPattern

describe PathPattern do
  it "must parse root from pattern" do
    path = PathPattern.new "ext/java/**/*.java"
    path.root.to_s.must_equal "ext/java"
  end

  describe "matching" do
    before do
      @pattern1 = PathPattern.new "ext/java/**/*.java"
      @pattern2 = PathPattern.new "ext/java"
    end

    it "must match" do
      @pattern1.match("ext/java/OnChange.java").must_equal true
      @pattern1.match("ext/java/org/substantiality/PathPattern.java").must_equal true

      @pattern2.match("ext/java/PathPattern.java").must_equal true
      @pattern2.match("ext/java/PathPattern.rb").must_equal true
    end

    it "wont match" do
      @pattern1.match("test/api_spec.rb").wont_equal true
      @pattern1.match("ext/java/org/substantiality/PathPattern.rb").wont_equal true

      @pattern2.match("lib/ruby/PathPattern.rb").wont_equal true
    end
  end
end
