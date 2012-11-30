#!/usr/bin/env jruby

# encoding: utf-8

require_relative "helper"

java_import java.nio.file.StandardWatchEventKinds
java_import org.substantiality.OnChange

describe OnChange do

  describe "new" do
    it "must take an Array of Paths to monitor" do
      OnChange.new("ext/java", "test")
    end
  end

  describe "filter" do
    it "must take a list of matchers" do
      OnChange.new.filter("*.java", "*.rb")
    end
  end

  describe "bind" do
    it "must require a PathChangeHandler" do
      OnChange.new(".").bind { |path, event| }
    end

    it "must block" do
      watcher = Thread.new do
        OnChange.new(".").bind { |_,_| }
      end
      sleep 1
      watcher.must_be :alive?
    end
  end

  describe "OnChange method" do
    it "must be a short-cut to OnChange.new" do
      OnChange("ext/java", "test").filter("*.java", "*.rb").bind { |path, event| }
    end
  end

  describe "events" do
    describe StandardWatchEventKinds::ENTRY_CREATE do

    end

    describe StandardWatchEventKinds::ENTRY_DELETE do

    end

    describe StandardWatchEventKinds::ENTRY_MODIFY do

    end
  end
end
